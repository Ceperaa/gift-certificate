package ru.clevertec.ecl.services;

import java.util.List;

public interface EntityService<T> {

    T findById(Long id);

    Long getSequence();

    List<T> findByFirstIdAndLastId(Long firstId, Long lastId);

    Long nextval();
}
