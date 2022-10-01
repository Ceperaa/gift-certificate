package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.model.entity.Users;
import ru.clevertec.ecl.model.entity.Users_;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @EntityGraph(attributePaths = Users_.ORDERS)
    Optional<Users> findById(Long id);
}
