package ru.clevertec.ecl.interceptors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static ru.clevertec.ecl.util.Constant.*;

@Component
@RequiredArgsConstructor
public class EntitiesInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (super.isRepeat(request)) {
            return true;
        }
        switch (request.getMethod()) {
            case GET:
                return true;
            case DELETE:
                delete(request);
                return false;
            case PUT:
                super.response(response, update(request));
                return false;
            default:
                super.response(response, save(request));
                return true;
        }
    }

    private List<Object> save(HttpServletRequest request) {
        Object body = bodyRead(request);
        return ports
                .stream()
                .filter(port -> !port.equals(localServerPort))
                .map(port -> CompletableFuture.runAsync(() ->
                        restTemplate.postForObject(super.replacePort(request, port), body, Object.class)))
                .collect(toList());
    }

    private Object update(HttpServletRequest request) {
        Object body = getBody(request);
        return ports
                .stream()
                .filter(port -> !port.equals(localServerPort))
                .map(port -> CompletableFuture.runAsync(() ->
                        restTemplate.patchForObject(super.replacePort(request, port), body, Object.class)))
                .collect(toList());
    }

    private void delete(HttpServletRequest request) {
        ports.forEach(port -> CompletableFuture.runAsync(() ->
                restTemplate.getForObject(super.replacePort(request, port), Object.class)));
    }

    @SneakyThrows(IOException.class)
    private Object getBody(HttpServletRequest request) {
        BodyInputStreamWrapper wrapper = (BodyInputStreamWrapper) request;
        return new Gson()
                .fromJson(wrapper.getReader().lines().collect(joining(System.lineSeparator())), Object.class);
    }

    @SneakyThrows(IOException.class)
    private Object bodyRead(HttpServletRequest request) {
        return new Gson().fromJson(
                new BufferedReader(
                        new InputStreamReader(request.getInputStream()))
                        .lines().reduce("", String::concat), Object.class);
    }
}
