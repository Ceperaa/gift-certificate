package ru.clevertec.ecl.repository.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.model.entity.GiftCertificateTag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateTagRowMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificateTag.builder()
                .giftCertificateId(rs.getLong("gift_certificate"))
                .tagId(rs.getLong("tag_id"))
                .build();
    }
}
