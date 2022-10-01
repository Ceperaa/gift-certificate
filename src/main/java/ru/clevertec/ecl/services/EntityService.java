package ru.clevertec.ecl.services;

public interface EntityService<T> {

    T findById(Long id);
}
