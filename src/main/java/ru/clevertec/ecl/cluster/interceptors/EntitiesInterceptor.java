package ru.clevertec.ecl.cluster.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.cluster.Nodes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static ru.clevertec.ecl.util.Constant.*;

@Component
@Slf4j
public class EntitiesInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final Nodes nodes;

    public EntitiesInterceptor(RestTemplate restTemplate, RestTemplate restTemplate1, Nodes nodes) {
        super(restTemplate);
        this.restTemplate = restTemplate1;
        this.nodes = nodes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("entity preHandle. Request URL = " + request.getRequestURL().toString());
        log.debug("entity preHandle. request method = " + request.getMethod());
        super.localHostPort = super.currentHostPort(request.getRequestURL().toString());
        log.debug("entity preHandle. localHostPort = " + super.localHostPort);
        if (super.isRepeat(request)) {
            return true;
        }
        Map<Long, List<String>> activeNode = nodes.getActiveNode();
        switch (request.getMethod()) {
            case GET:
                return true;
            case DELETE:
                doDelete(request, activeNode);
                return false;
            case PUT:
                doPatch(request, activeNode);
                return false;
            default:
                return doPost(request, activeNode);
        }
    }

    private boolean doPost(HttpServletRequest request, Map<Long, List<String>> activeNones) {
        activeNones.values().forEach(value -> value
                .stream()
                .filter(hostPort -> !hostPort.equals(super.localHostPort))
                .forEach(hostPort -> super.saveInNode(request, hostPort, COMMIT_LOG)));
        return super.saveLocal(request, COMMIT_LOG);
    }

    private void doPatch(HttpServletRequest request, Map<Long, List<String>> activeNones) {

        Object body = super.bodyRead(request);
        log.debug("entity update. body - " + body);
        activeNones.forEach((key, value) -> value
                .stream()
                .filter(hostPort -> !hostPort.equals(super.localHostPort))
                .peek(hostPort -> super.saveCommitLog(request, hostPort, super.getEntityName(hostPort), COMMIT_LOG))
                .forEach(hostPort -> super.supplyAsync(
                        restTemplate.patchForObject(super.replacePort(request, hostPort), body, Object.class))));
    }

    private void doDelete(HttpServletRequest request, Map<Long, List<String>> activeNones) {
        activeNones.forEach((key, value) ->
                value
                        .stream()
                        .peek(hostPort -> super.saveCommitLog(request, hostPort, super.getEntityName(hostPort),
                                COMMIT_LOG))
                        .forEach(hostPort -> CompletableFuture.runAsync(() ->
                                        restTemplate.getForObject(super.replacePort(request, hostPort), Object.class),
                                Executors.newFixedThreadPool(POOL_SIZE))));
    }
}
