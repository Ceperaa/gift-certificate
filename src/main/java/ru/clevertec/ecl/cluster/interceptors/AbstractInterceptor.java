package ru.clevertec.ecl.cluster.interceptors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.model.entity.CommitLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.ecl.util.Constant.*;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractInterceptor {

    private final RestTemplate restTemplate;

    @Value("${server.port}")
    protected String localServerPort;

    protected String localHostPort;

    @SneakyThrows()
    protected void response(HttpServletResponse response, Object o) {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter.write(new Gson().toJson(o));
        }
    }

    protected String getEntityName(String url) {
        return validatorHandler(url, REGEX_ENTITIES);
    }

    protected boolean isRepeat(HttpServletRequest request) {
        return request.getHeader(REPEAT) != null;
    }

    private static String validatorHandler(String url, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(url);
        return (matcher.find()) ? matcher.group() : "";
    }

    protected String currentHostPort(String url) {
        return validatorHandler(url, REGEX_HOST_PORT);
    }

    protected String replacePort(HttpServletRequest request, String hostPort) {
        String s = currentHostPort(request.getRequestURL().toString());
        return String.format("%s?%s", request.getRequestURL().toString().replace(s, hostPort),
                Optional.ofNullable(request.getQueryString()).orElse(""));
    }

    @SneakyThrows(IOException.class)
    protected Object bodyRead(HttpServletRequest request) {
        return new Gson().fromJson(
                new BufferedReader(
                        new InputStreamReader(request.getInputStream()))
                        .lines().reduce("", String::concat), Object.class);
    }

    protected CommitLog buildCommitLog(String entityName, HttpServletRequest request, Long lastSequence) {
        return CommitLog
                .builder()
                .entityName(entityName)
                .method(HttpMethod.valueOf(request.getMethod()))
                .sequence(lastSequence)
                .build();
    }

    protected Long findNextvalSequence(String hostPort, String entityName) {
        log.debug("findNextvalSequence. hostPort = " + hostPort);
        return restTemplate
                .getForObject(String.format(HTTP_URL, hostPort, entityName, SEQUENCE_NEXTVAL), Long.class);
    }

    protected Object saveInNode(HttpServletRequest request, String hostPort, String commitLogName) {
        Map body = (Map) bodyRead(request);
        String url = replacePort(request, hostPort);
        body.put("id", saveCommitLog(request, hostPort, getEntityName(url),commitLogName));
        Object object = restTemplate.postForObject(url, body, Object.class);
        log.debug("order save. Order = " + object);
        return object;
    }

    protected boolean saveLocal(HttpServletRequest request, String commitLogName) {
        Map body = (Map) bodyRead(request);
        BodyInputStreamWrapper wrapper = (BodyInputStreamWrapper) request;
        body.put(ID, saveCommitLog(request, localHostPort, getEntityName(replacePort(request, localHostPort)),commitLogName));
        wrapper.setLine(new Gson().toJson(body));
        return true;
    }

    protected Long saveCommitLog(HttpServletRequest request, String hostPort, String entityName, String commitLogName) {
        log.debug("saveCommitLog. hostPort = " + hostPort);
        Long lastSequence = findNextvalSequence(hostPort, entityName);
        Object o = restTemplate.postForObject(String.format(HTTP_URL, hostPort, commitLogName, ""),
                buildCommitLog(entityName, request, lastSequence), Object.class);
        log.debug("save commit log = " + o);
        return lastSequence;
    }


}
