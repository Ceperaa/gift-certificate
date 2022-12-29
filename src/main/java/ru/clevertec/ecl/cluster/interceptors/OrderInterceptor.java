package ru.clevertec.ecl.cluster.interceptors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.cluster.Nodes;
import ru.clevertec.ecl.exception.NodeNotActiveException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.clevertec.ecl.util.Constant.*;

@Component
@Slf4j
public class OrderInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final Nodes nodes;

    public OrderInterceptor(RestTemplate restTemplate, RestTemplate restTemplate1, Nodes nodes) {
        super(restTemplate);
        this.restTemplate = restTemplate1;
        this.nodes = nodes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("order preHandle. Request URL = " + request.getRequestURL().toString());
        log.debug("order preHandle. request method = " + request.getMethod());
        super.localHostPort = super.currentHostPort(request.getRequestURL().toString());
        if (super.isRepeat(request)) {
            return true;
        }
        Map<Long, List<String>> activeNode = nodes.getActiveNode();
        switch (request.getMethod()) {
            case GET:
                doGet(request, response, activeNode);
            case DELETE:
                return doDelete(request, activeNode);
            default:
                return doPost(request, response, activeNode);
        }
    }

    private boolean doPost(HttpServletRequest request, HttpServletResponse response, Map<Long, List<String>> activeNode) {
        NodeData nodeData = minSequence(activeNode);
        activeNode.get(nodeData.idGroup)
                .stream()
                .filter(hostPort -> !hostPort.equals(super.localHostPort))
                .forEach(hostPort -> super.response(response, super.saveInNode(request, hostPort, ORDER_COMMIT_LOG)));
        return (activeNode.get(nodeData.idGroup).contains(super.localHostPort)) && super.saveLocal(request
                , ORDER_COMMIT_LOG);
    }

    private boolean doDelete(HttpServletRequest request, Map<Long, List<String>> stringListMap) {
        List<String> shard = getShard(findId(request), stringListMap);
        shard
                .stream()
                .filter(hostPort -> !hostPort.equals(super.localHostPort))
                .forEach(hostPort ->
                        CompletableFuture.runAsync(() -> restTemplate.delete(super.replacePort(request, hostPort),
                                Executors.newFixedThreadPool(POOL_SIZE))));
        return (shard.contains(super.localHostPort));
    }

    private void doGet(HttpServletRequest request, HttpServletResponse
            response, Map<Long, List<String>> activeNode) {
        String id = findId(request);
        if (id.isEmpty()) {
            super.response(response, findAll(request));
        } else {
            String hostPort = getShard(id, activeNode)
                    .stream()
                    .filter(hostport -> !hostport.isEmpty())
                    .findFirst().orElseThrow(NodeNotActiveException::new);
            Object object = restTemplate.getForObject(super.replacePort(request, hostPort), Object.class);
            super.response(response, object);
        }
    }

    private List<Object> findAll(HttpServletRequest request) {
        List<Object> list = new ArrayList<>();
        log.debug("order findAll. list = " + list);
        nodes.getActiveNode().values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(hostPort -> list.addAll(Collections.singleton(
                        restTemplate.getForObject(super.replacePort(request, hostPort), Object.class))));
        return list;
    }

    private NodeData minSequence(Map<Long, List<String>> stringListMap) {
        return stringListMap.entrySet()
                .stream()
                .filter(longListEntry -> !longListEntry.getValue().isEmpty())
                .map(entry -> buildNodeData(entry.getKey(), entry.getValue()))
                .min(Comparator.comparing(nodeDataBuilder -> nodeDataBuilder.sequence))
                .orElseThrow(NodeNotActiveException::new);
    }

    private String getPortInGroup(List<String> stringListEntry) {
        return stringListEntry
                .stream()
                .filter(list -> !list.isEmpty())
                .findFirst().orElseThrow(NodeNotActiveException::new);
    }

    private NodeData buildNodeData(Long idGroup, List<String> hostPortList) {
        return NodeData.builder()
                .sequence(findSequence(getPortInGroup(hostPortList)))
                .port(getPortInGroup(hostPortList)).idGroup(idGroup)
                .build();
    }

    private List<String> getShard(String id, Map<Long, List<String>> stringListMap) {
        long idGroup = Long.parseLong(id) % stringListMap.size();
        return stringListMap.get(idGroup);
    }

    private static String findId(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        log.debug("order findId. url = " + url);
        Matcher matcher = Pattern.compile(ORDERS + REGEX_DIGITS).matcher(url);
        log.debug("order findId. matcher = " + matcher);
        return (matcher.find()) ? matcher.group(1) : "";
    }

    private Long findSequence(String hostPort) {
        return restTemplate.getForObject(String.format(HTTP_URL, hostPort, ORDERS, SEQUENCE), Long.class);
    }

    @AllArgsConstructor
    @Builder
    @Data
    private static class NodeData {
        private Long idGroup;
        private Long sequence;
        private String port;

    }
}
