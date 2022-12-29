package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, RepositoryEntity<Order> {

    @EntityGraph(attributePaths = "user")
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    @Query(value = "SELECT (CASE WHEN is_called THEN last_value" +
            "                         ELSE last_value - 1 END ) AS nextvalue" +
            "            FROM orders_id_seq ", nativeQuery = true)
    Long getValMySequence();
}
