package ru.clevertec.ecl.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.clevertec.ecl.util.Page;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractRepositoryCrud<T> implements RepositoryCrud<T> {

    private final String select;
    private final String delete;
    private final String selectAll;
    private final String insert;
    private final String tableName;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<T> mapper;

    @Override
    public List<T> findAll(Page page) {
        Map<String, Serializable> limit = Map
                .of("limit", Optional.ofNullable(page.getLimit()).orElseGet(() -> DEFAULT_PAGE_SIZE),
                        "offset", Optional.ofNullable(page.getOffset()).orElseGet(() -> 0));

        return namedParameterJdbcTemplate
                .query(selectAll, limit, mapper);
    }

    @Override
    public T create(T model) {
        final KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                insert, new MapSqlParameterSource(mapObject(model)),
                holder,
                new String[]{"id"}
        );
        setId(model, Objects.requireNonNull(holder.getKey()).longValue());
        return model;
    }

    @Override
    public void deleteById(Long id) {
        namedParameterJdbcTemplate.update(delete, Map.of("id", id));
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional
                .ofNullable(
                        namedParameterJdbcTemplate.queryForObject(select, Map.of("id", id), mapper));
    }

    @Override
    public Optional<T> updateFieldsById(Map<String, Object> packageFields, Long id) {
        packageFields.put("id", id);
        String fields = packageFields.keySet()
                .stream()
                .map(keyName -> keyName + " = :" + keyName)
                .collect(Collectors.joining(", "));

        String SQL = String.format("update %s set %s where id = :id", tableName, fields);
        namedParameterJdbcTemplate.update(SQL, packageFields);
        return findById(id);
    }

    protected Map<String, Object> mapObject(T model) {
        return new ObjectMapper().
                convertValue(model, new TypeReference<>() {
                });
    }

    abstract protected void setId(T model, long id);
}