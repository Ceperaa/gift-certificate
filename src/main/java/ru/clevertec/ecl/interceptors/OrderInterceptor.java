package ru.clevertec.ecl.interceptors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.ecl.util.Constant.*;

@Component
@RequiredArgsConstructor
public class OrderInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (super.isRepeat(request)) {
            return true;
        }
        switch (request.getMethod()) {
            case GET:
                get(request, response);
            case DELETE:
                delete(request);
            default:
                save(request, response);
        }
        return false;
    }

    private Long seq(String port) {
        return restTemplate.getForObject(String.format(String.format(SEQUENCE, ORDERS), port), Long.class);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        NodeData nodeData = ports
                .stream().map(port -> NodeData.builder().sequence(seq(port)).port(port).build())
                .min(Comparator.comparing(NodeData::getSequence)).orElseThrow(RuntimeException::new);
        Object object = restTemplate.postForObject(super.replacePort(request, nodeData.port), null, Object.class);
        super.response(response, object);
    }

    private void delete(HttpServletRequest request) {
        restTemplate.delete(super.replacePort(request, getPort(validatorHandler(request).orElse("0"))));
    }

    private void get(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> id = validatorHandler(request);
        if (id.isEmpty()) {
            super.response(response, findAll(request));
        }
        String port = getPort(id.orElseThrow(RuntimeException::new));
        Object object = restTemplate.getForObject(super.replacePort(request, port), Object.class);
        super.response(response, object);
    }

    private List<Object> findAll(HttpServletRequest request) {
        List<Object> list = new ArrayList<>();
        ports.forEach(url -> list.addAll(Collections.singleton(
                restTemplate.getForObject(super.replacePort(request, url), Object.class))));
        return list;
    }

    private static Optional<String> validatorHandler(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Matcher matcher = Pattern.compile(ORDERS + REGEX_DIGITS).matcher(url);
        return (matcher.find()) ? Optional.of(matcher.group(1)) : Optional.empty();
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
