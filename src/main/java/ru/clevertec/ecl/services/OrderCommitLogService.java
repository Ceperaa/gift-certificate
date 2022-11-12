package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.entity.OrderCommitLog;

import java.util.List;

public interface OrderCommitLogService {

    OrderCommitLog saveCommitLog(OrderCommitLog commitLog);

    Long getSequence();

    List<OrderCommitLog> findByFirstIdAndLastId(Long firstId, Long lastId);
}
