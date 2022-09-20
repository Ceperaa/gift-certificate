package ru.clevertec.ecl.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.rowMapper.TagRowMapper;

@Repository
public class TagRepositoryCrud extends AbstractRepositoryCrud<Tag> {

    private static final String SELECT =
            "select id, name " +
                    " from tag " +
                    " where id = :id ";
    private static final String DELETE =
            "delete from tag" +
                    " where id = :id ";
    private static final String SELECT_ALL =
            "select id,name " +
                    "from tag " +
                    "order by id asc limit :limit offset :offset";
    private static final String TABLE_NAME = "tag";
    private static final String INSERT =
            "insert into tag (name)" +
                    " values (:name)";
    private static final TagRowMapper MAPPER = new TagRowMapper();

    public TagRepositoryCrud(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        super(SELECT, DELETE, SELECT_ALL, INSERT, TABLE_NAME, namedParameterJdbcTemplate, MAPPER);
    }

    @Override
    protected void setId(Tag model, long id) {
        model.setId(id);
    }
}
