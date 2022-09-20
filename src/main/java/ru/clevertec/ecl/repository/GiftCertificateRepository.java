package ru.clevertec.ecl.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.repository.rowMapper.GiftCertificateRowMapper;

import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepository extends AbstractRepositoryCrud<GiftCertificate> implements RepositorySearch<GiftCertificate> {

    private static final String SELECT =
            " select g.id, g.name, g.description, g.duration, g.price, g.create_date, g.last_update_date " +
                    " from gift_certificate g " +
                    " where g.id = :id ";
    private static final String DELETE =
            " delete from gift_certificate" +
                    " where id = :id ";
    private static final String SELECT_ALL =
            " select g.id,g.name,g.description,g.duration,g.price,g.create_date,g.last_update_date " +
                    "from gift_certificate g " +
                    "order by id asc limit :limit offset :offset";
    private static final String INSERT =
            " insert into gift_certificate (name, description, price, duration, create_date, last_update_date) " +
                    " values (:name, :description,:price, :duration, :create_date, :last_update_date) ";
    private static final String SELECT_BY_TAG =
            " select gc.id, gc.name, gc.description, gc.duration, gc.price, gc.create_date, gc.last_update_date" +
                    " from tag t" +
                    " join gift_certificate_tag gct on t.id = gct.tag_id" +
                    " join gift_certificate gc on gct.gift_certificate_id = gc.id" +
                    " where t.name = :field ";
    private static final String SELECT_BY_NAME_OR_DESCRIPTION =
            " select gc.id, gc.name, gc.description, gc.duration, gc.price, gc.create_date, gc.last_update_date" +
                    " from gift_certificate gc " +
                    " where gc.name LIKE :name or gc.description LIKE :description ";
    private static final GiftCertificateRowMapper MAPPER = new GiftCertificateRowMapper();
    private static final String TABLE_NAME = "gift_certificate";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GiftCertificateRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(SELECT, DELETE, SELECT_ALL, INSERT, TABLE_NAME, namedParameterJdbcTemplate, MAPPER);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    protected Map<String, Object> mapObject(GiftCertificate giftCertificateDto) {
        Map<String, Object> stringObjectMap = new ObjectMapper().
                convertValue(giftCertificateDto, new TypeReference<>() {
                });
        stringObjectMap.put("create_date", giftCertificateDto.getCreateDate());
        stringObjectMap.put("last_update_date", giftCertificateDto.getLastUpdateDate());
        return stringObjectMap;
    }

    protected void setId(GiftCertificate model, long id) {
        model.setId(id);
    }

    public List<GiftCertificate> findByTagName(String tagNames) {

        return namedParameterJdbcTemplate.query(SELECT_BY_TAG, Map.of("field", tagNames), MAPPER);
    }

    @Override
    public List<GiftCertificate> findByTagNameOrDescription(String name, String description) {
        return namedParameterJdbcTemplate
                .query(SELECT_BY_NAME_OR_DESCRIPTION,
                        Map.of("name", String.format("%%%s%%", name),
                                "description", String.format("%%%s%%", description)),
                        MAPPER);
    }


        }


