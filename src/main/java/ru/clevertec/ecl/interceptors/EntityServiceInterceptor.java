package ru.clevertec.ecl.interceptors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static ru.clevertec.ecl.util.Constant.*;

@Component
@RequiredArgsConstructor
public class EntityServiceInterceptor {

    @Value("${ports}")
    private List<String> ports;

    @Value("${server.port}")
    private String localServerPort;
    private final RestTemplate restTemplate;

    public boolean buildRequest(HttpServletRequest request, HttpServletResponse response) {
        switch (request.getMethod()) {
            case GET:
                return true;
            case DELETE:
                delete(request);
                return false;
            case PUT:
                RequestProcessing.response(response, update(request));
                return false;
            default:
                RequestProcessing.response(response, save(request));
                return false;
        }
    }

    private List<Object> save(HttpServletRequest request) {
        Object body = getBody(request);
        return ports
                .stream()
                .map(port -> restTemplate.postForObject(replacePort(request, port), body, Object.class))
                .collect(toList());
    }

    private Object update(HttpServletRequest request) {
        Object body = getBody(request);
        return ports
                .stream()
                .map(port -> restTemplate.patchForObject(replacePort(request, port), body, Object.class))
                .collect(toList());
    }

    private void delete(HttpServletRequest request) {
        ports.forEach(port -> restTemplate.getForObject(replacePort(request, port), Object.class));
    }

    private String replacePort(HttpServletRequest request, String port) {
        return String.format("%s?%s", request.getRequestURL().toString().replace(localServerPort, port),
                Optional.ofNullable(request.getQueryString()).orElse(""));
    }

    @SneakyThrows(IOException.class)
    private Object getBody(HttpServletRequest request) {
        ContentCachingRequestWrapper wrappedReq = (ContentCachingRequestWrapper) request;
        return new Gson()
                .fromJson(wrappedReq.getReader().lines().collect(joining(System.lineSeparator())), Object.class);
    }
}
