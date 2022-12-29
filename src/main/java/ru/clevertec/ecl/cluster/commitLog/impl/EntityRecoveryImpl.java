package ru.clevertec.ecl.cluster.commitLog.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.cluster.Nodes;
import ru.clevertec.ecl.cluster.commitLog.CommitLogList;
import ru.clevertec.ecl.cluster.commitLog.EntityRecovery;
import ru.clevertec.ecl.model.entity.CommitLog;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.clevertec.ecl.util.Constant.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class EntityRecoveryImpl extends AbstractLog implements EntityRecovery {

    private final RestTemplate restTemplate;
    private final Nodes nodes;

    private Long findSequence(String hostPort) {
        String format = String.format(HTTP_URL, hostPort, COMMIT_LOG, SEQUENCE);
        return restTemplate.getForObject(format, Long.class);
    }

    private Map<String, Long> hostPortAndSequence() {
        return nodes.getActiveNode().values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors
                        .toMap(hostPort -> hostPort, this::findSequence, (a, b) -> b));
    }

    public void run() {
        hostPortAndSequence()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(super.localServerPort))
                .forEach(entry -> commitLogListMinMax(entry.getValue())
                        .stream()
                        .filter(commitLogListMinMax -> !entry.getValue().equals(maxSequence().getValue()))
                        .forEach(commitLogListMinMax -> saveObj(entry.getKey(), commitLogListMinMax)));
    }

    private Object doGet(CommitLog commitLog) {
        String getUrl = buildUrl(maxSequence().getKey(), commitLog.getEntityName(),
                "/".concat(String.valueOf(commitLog.getSequence())));
        return restTemplate.getForObject(getUrl, Object.class);
    }

    private void saveObj(String hostPort, CommitLog commitLog) {
        if (!commitLog.getMethod().equals(HttpMethod.DELETE)) {
            String url = buildUrl(hostPort, commitLog.getEntityName(), RECOVERY);
            saveCommitLog(hostPort, commitLog);
            restTemplate.postForObject(url, doGet(commitLog), Object.class);
        } else {
            restTemplate.delete(buildUrl(hostPort, commitLog.getEntityName(),
                    "/".concat(String.valueOf(commitLog.getSequence()))));
        }
    }

    private void saveCommitLog(String hostPort, CommitLog commitLog) {
        restTemplate.postForObject(buildUrl(hostPort, COMMIT_LOG, ""), commitLog, CommitLog.class);
    }

    private List<CommitLog> commitLogListMinMax(Long minSequence) {
        String url = String.format(
                HTTP_URL,
                maxSequence().getKey(),
                COMMIT_LOG,
                String.format(RANGE, minSequence, maxSequence().getValue()));
        CommitLogList forObject = restTemplate.getForObject(url, CommitLogList.class);
        return forObject.getCommitLogs();
    }

    private Map.Entry<String, Long> maxSequence() {
        return hostPortAndSequence().entrySet()
                .stream()
                .filter(entry -> !entry.getKey().contains(localServerPort))
                .max(Map.Entry.comparingByValue()).orElseThrow(RuntimeException::new);
    }
}