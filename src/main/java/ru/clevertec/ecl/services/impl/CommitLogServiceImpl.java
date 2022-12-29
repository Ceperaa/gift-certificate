package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.model.entity.CommitLog;
import ru.clevertec.ecl.repository.CommitLogRepository;
import ru.clevertec.ecl.services.CommitLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommitLogServiceImpl implements CommitLogService {

    private final CommitLogRepository repository;

    @Override
    public CommitLog saveCommitLog(CommitLog commitLog) {
        return repository.save(commitLog);
    }

    @Override
    public Long getSequence() {
        return repository.getNextValMySequence();
    }

    @Override
    public List<CommitLog> findByFirstIdAndLastId(Long firstId, Long lastId) {
        return repository.findByIdBetween(firstId, lastId);
    }
}
