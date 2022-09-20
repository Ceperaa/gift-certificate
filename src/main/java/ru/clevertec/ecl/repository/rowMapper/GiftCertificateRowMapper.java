package ru.clevertec.ecl.repository.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.util.LocalDateStringConvert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(LocalDateStringConvert.DATA_FORMAT);

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .duration(rs.getInt("duration"))
                .createDate(LocalDateTime.parse(rs.getString("create_date"), DATE_TIME_FORMATTER))
                .lastUpdateDate(LocalDateTime.parse(rs.getString("last_update_date"),DATE_TIME_FORMATTER))
                .build();
    }
}
