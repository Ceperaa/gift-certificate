package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.util.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepositoryCrud<E> {

    List<E> findAll(Page page);

    E create(E o);

    void deleteById(Long id);

    Optional<E> findById(Long id);

    Optional<E> updateFieldsById(Map<String, Object> keys, Long id);
}
