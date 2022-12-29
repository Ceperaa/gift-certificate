package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.OrderCommitLog;

import java.util.List;

public interface OrderCommitLogRepository extends JpaRepository<OrderCommitLog,Long> {

    @Query(value = "SELECT (CASE WHEN is_called THEN last_value ELSE 0 END )" +
            " AS nextvalue FROM orders_id_seq", nativeQuery = true)
    Long getValMySequence();

    List<OrderCommitLog> findByIdBetween(Long firstId, Long LastId);


}
