package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, RepositoryEntity<Tag> {

    Optional<Tag> findTagByName(String name);

    @Query(value = " SELECT (CASE WHEN is_called THEN last_value" +
            "                         ELSE last_value - 1 END ) AS nextvalue" +
            "            FROM tag_id_seq", nativeQuery = true)
    Long getNextValMySequence();

}
