package ru.clevertec.ecl.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.GiftCertificateTag;
import ru.clevertec.ecl.repository.rowMapper.GiftCertificateTagRowMapper;

@Repository
public class GiftCertificateTagRepository extends AbstractRepositoryCrud<GiftCertificateTag> {

    private static final String SELECT =
            "select tag_id, gift_certificate " +
                    "from gift_certificate_tag " +
                    "where tag_id = :id ";
    private static final String DELETE =
            "delete from gift_certificate_tag" +
                    " where id = :id ";
    private static final String SELECT_ALL =
            "select id, tag_id ,gift_certificate " +
                    "from gift_certificate_tag " +
                    "order by id asc limit :limit offset :offset";
    private static final String TABLE_NAME = "gift_certificate_tag";
    private static final String INSERT =
            "insert into gift_certificate_tag (tag_id, gift_certificate)" +
                    " values (:tagId, :giftCertificate)";
    private static final GiftCertificateTagRowMapper MAPPER = new GiftCertificateTagRowMapper();

    public GiftCertificateTagRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(SELECT, DELETE, SELECT_ALL, INSERT, TABLE_NAME, namedParameterJdbcTemplate, MAPPER);
    }

    @Override
    protected void setId(GiftCertificateTag model, long id) {
        model.setId(id);
    }
}
