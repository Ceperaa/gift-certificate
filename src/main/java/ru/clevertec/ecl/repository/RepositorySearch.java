package ru.clevertec.ecl.repository;

import java.util.List;

public interface RepositorySearch<T> extends RepositoryCrud<T> {

    List<T> findByTagName(String tagNames);

    List<T> findByTagNameOrDescription(String name, String description);
}
