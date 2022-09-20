package ru.clevertec.ecl.repository.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
