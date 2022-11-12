package ru.clevertec.ecl.cluster.healsCheck.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.cluster.Nodes;
import ru.clevertec.ecl.cluster.healsCheck.HealsCheck;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class HealsCheckImpl implements HealsCheck {

    @Value("${server.port}")
    protected String localServerPort;

    private final Nodes nodes;
    private final RestTemplate restTemplate;

    public void checkShards() {
        Map<Long, List<String>> activeNode = nodes.getActiveNode();
        nodes.getNodes().entrySet()
                .forEach(value -> activeNode.put(value.getKey(), activePorts(value)));
        log.debug("active node list - " + activeNode.values()
                .stream()
                .map(strings -> String.join(",", strings))
                .collect(Collectors.joining(",")));
    }

    private List<String> activePorts(Map.Entry<Long, List<String>> value) {
        List<String> activePort = value.getValue()
                .stream()
                .filter(hostPort -> hostPort.contains(localServerPort) || codeStatus(buildUrl(hostPort)))
                .peek(port -> log.debug("active port in Shard - " + value + " = " + port))
                .collect(Collectors.toList());
        log.debug("active port list - " + String.join("", activePort));
        return activePort;
    }

    private boolean codeStatus(String url) {
        log.debug("codeStatus. url post replace = " + url);
           int codeStatus = checkQuery(url);
            if (codeStatus == 400) return false;
            log.debug("contextInitialized. code status = " + codeStatus);
        return true;
    }

    private int checkQuery(String url) {
        ResponseEntity<?> forEntity;
        try {
            forEntity = restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            forEntity = ResponseEntity.badRequest().build();
            log.debug("checkQuery. repeat ");
        }
        log.debug("contextInitialized. ResponseEntity = " + forEntity.toString());
        return forEntity.getStatusCodeValue();
    }

    protected String buildUrl(String hostHort) {
        String replace = "http://".concat(hostHort).concat("/api/check");
        log.debug("replacePort. replace port = " + replace);
        return replace;
    }
}
