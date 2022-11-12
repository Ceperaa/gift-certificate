package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.entity.OrderCommitLog;
import ru.clevertec.ecl.repository.OrderCommitLogRepository;
import ru.clevertec.ecl.services.OrderCommitLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCommitLogServiceImpl implements OrderCommitLogService {

    private final OrderCommitLogRepository repository;

    @Override
    public OrderCommitLog saveCommitLog(OrderCommitLog commitLog) {
        return repository.save(commitLog);
    }

    @Override
    public Long getSequence() {
        return repository.getValMySequence();
    }

    @Override
    public List<OrderCommitLog> findByFirstIdAndLastId(Long firstId, Long lastId) {
        return repository.findByIdBetween(firstId, lastId);
    }
}
