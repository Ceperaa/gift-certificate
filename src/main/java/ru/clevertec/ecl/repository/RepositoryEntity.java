package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryEntity<T> {

    List<T> findByIdBetween(Long firstId, Long LastId);

    @Query(value = "SELECT nextval(?)", nativeQuery = true)
    Long nextval(String seqName);

    @Query(value = "SELECT setval(?, ? )", nativeQuery = true)
    Long setval(String seqName, Long id);
}
