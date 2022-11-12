package ru.clevertec.ecl.cluster.commitLog.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.cluster.Nodes;
import ru.clevertec.ecl.cluster.commitLog.OrderCommitLogList;
import ru.clevertec.ecl.cluster.commitLog.OrderRecovery;
import ru.clevertec.ecl.exception.NodeNotActiveException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.dto.OrderCommitLogDto;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.entity.OrderCommitLog;

import java.util.*;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static ru.clevertec.ecl.util.Constant.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderRecoveryImpl extends AbstractLog implements OrderRecovery {

    private final RestTemplate restTemplate;
    private final Nodes nodes;
    private final OrderMapper mapper;

    public void run() {
        Map<Long, List<String>> activeNode = nodes.getActiveNode();
        Map<Long, SequencePort> max = maxSeqOneGroup(activeNode);
        LongStream.range(0, activeNode.size())
                .forEach(i -> seqGroupList(activeNode)
                        .stream()
                        .filter(sequencePort -> sequencePort.hostPort.contains(localServerPort) &&
                                sequencePort.idGroup.equals(i))
                        .forEach(sequencePort -> commitLogListMinMax(sequencePort.sequence,
                                max.get(i).sequence, max.get(i).hostPort)
                                .forEach(commitLogListMinMax -> saveOrder(commitLogListMinMax, max.get(i).getHostPort(),
                                        sequencePort.hostPort))));
    }

    private Order doGet(OrderCommitLog commitLog, String maxPort) {
        String url = super.buildUrl(maxPort, ORDERS, String.format("/%d/%s", commitLog.getSequence(), RECOVERY));
        OrderCommitLogDto forObject = restTemplate.getForObject(url, OrderCommitLogDto.class);
        return mapper.toEntity(forObject);
    }

    private void saveOrder(OrderCommitLog commitLog, String maxPort, String localHostPort) {
        if (!commitLog.getMethod().equals(HttpMethod.DELETE)) {
            String url = super.buildUrl(localHostPort, ORDERS, RECOVERY);
            restTemplate.postForObject(url, doGet(commitLog, maxPort), Object.class);
            saveCommit(localHostPort, commitLog);
        } else {
            restTemplate.delete(buildUrl(localHostPort, ORDERS, String.valueOf(commitLog.getSequence())));
        }
    }

    private void saveCommit(String hostPort, OrderCommitLog commitLog) {
        String url = buildUrl(hostPort, ORDER_COMMIT_LOG, "");
        restTemplate.postForObject(url, commitLog, OrderCommitLog.class);
    }

    private SequencePort findSequence(String hostPort, Long idGroup) {
        return SequencePort.builder()
                .sequence(restTemplate.getForObject(
                        String.format(HTTP_URL, hostPort, ORDER_COMMIT_LOG, SEQUENCE), Long.class))
                .idGroup(idGroup)
                .hostPort(hostPort).build();
    }

    private Map<Long, SequencePort> maxSeqOneGroup(Map<Long, List<String>> map) {
        return map.entrySet()
                .stream()
                .filter(longListEntry -> !longListEntry.getValue().isEmpty())
                .map(value -> value.getValue()
                        .stream()
                        .filter(hostPort -> !hostPort.contains(localServerPort))
                        .map(hostPort -> findSequence(hostPort, value.getKey()))
                        .max(Comparator.comparingLong(SequencePort::getSequence)))
                .collect(toMap(key -> key.orElseThrow(NodeNotActiveException::new).idGroup, Optional::get));
    }

    private List<SequencePort> seqGroupList(Map<Long, List<String>> map) {
        return map.entrySet()
                .stream()
                .flatMap(strings -> strings.getValue()
                        .stream()
                        .map(port -> findSequence(port, strings.getKey())))
                .collect(toList());
    }

    private List<OrderCommitLog> commitLogListMinMax(Long minSequence, Long maxSequence, String hostPort) {
        String url = String.format(
                HTTP_URL, hostPort, ORDER_COMMIT_LOG, String.format(RANGE, minSequence, maxSequence));
        return Objects.requireNonNull(restTemplate.getForObject(url, OrderCommitLogList.class)).getCommitLogs();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class SequencePort {
        private Long idGroup;
        private Long sequence;
        private String hostPort;
    }
}
