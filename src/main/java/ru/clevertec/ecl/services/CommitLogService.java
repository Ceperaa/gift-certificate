package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.entity.CommitLog;

import java.util.List;

public interface CommitLogService {

    CommitLog saveCommitLog(CommitLog commitLog);

    Long getSequence();

    List<CommitLog> findByFirstIdAndLastId(Long firstId, Long lastId);
}
