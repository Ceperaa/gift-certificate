package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.model.entity.Orders;
import ru.clevertec.ecl.model.entity.Orders_;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @EntityGraph(attributePaths = Orders_.USER)
    Optional<Orders> findByIdAndUserId(Long id, Long userId);
}
