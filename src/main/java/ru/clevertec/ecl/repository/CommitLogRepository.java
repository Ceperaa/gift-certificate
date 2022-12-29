package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.CommitLog;

import java.util.List;

public interface CommitLogRepository extends JpaRepository<CommitLog,Long> {


    @Query(value = "SELECT last_value FROM commit_log_id_seq", nativeQuery = true)
    Long getNextValMySequence();

    List<CommitLog> findByIdBetween(Long firstId, Long LastId);
}
