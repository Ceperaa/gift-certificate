package ru.clevertec.ecl.interceptors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.ecl.util.Constant.*;

@Component
@RequiredArgsConstructor
public class OrderServiceInterceptor {

    @Value("${ports}")
    private List<String> ports;

    @Value("${server.port}")
    private String localServerPort;

    private final RestTemplate restTemplate;

    public void buildRequest(HttpServletRequest request, HttpServletResponse response) {
        switch (request.getMethod()) {
            case GET:
                get(request, response);
            case DELETE:
                delete(request);
            default:
                save(request, response);
        }
    }

    private Long seq(String port) {
        return restTemplate.getForObject(String.format(String.format(SEQUENCE, ORDERS), port), Long.class);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        NodeData nodeData = ports
                .stream().map(port -> NodeData.builder().sequence(seq(port)).port(port).build())
                .min(Comparator.comparing(NodeData::getSequence)).orElseThrow(RuntimeException::new);
        Object object = restTemplate.postForObject(replacePort(request, nodeData.port), null, Object.class);
        RequestProcessing.response(response, object);
    }

    private void delete(HttpServletRequest request) {
        restTemplate.delete(replacePort(request, getPort(validatorHandler(request).orElse("0"))));
    }

    private void get(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> id = validatorHandler(request);
        if (id.isEmpty()) {
            RequestProcessing.response(response, findAll(request));
        }
        String port = getPort(id.orElseThrow(RuntimeException::new));
        Object object = restTemplate.getForObject(replacePort(request, port), Object.class);
        RequestProcessing.response(response, object);
    }

    private List<Object> findAll(HttpServletRequest request) {
        List<Object> list = new ArrayList<>();
        ports.forEach(url -> list.addAll(Collections.singleton(
                restTemplate.getForObject(replacePort(request, url), Object.class))));
        return list;
    }

    private static Optional<String> validatorHandler(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Matcher matcher = Pattern.compile(ORDERS + REGEX_DIGITS).matcher(url);
        return (matcher.find()) ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private String replacePort(HttpServletRequest request, String port) {
        return String.format("%s?%s", request.getRequestURL().toString().replace(localServerPort, port),
                Optional.ofNullable(request.getQueryString()).orElse(""));
    }

    private String getPort(String id) {
        Map<Integer, String> map = Map.of(
                0, ports.get(2),
                1, ports.get(0),
                2, ports.get(1)
        );
        return map.get(Integer.parseInt(id) % ports.size());
    }

    @Data
    @AllArgsConstructor
    @Builder
    private static class NodeData {
        private Long sequence;
        private String port;

    }
}



