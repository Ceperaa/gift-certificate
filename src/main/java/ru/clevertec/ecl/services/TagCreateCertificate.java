package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagCreateCertificate {

    Tag saveTag(Tag tag);

    List<Tag> createTagIfNotExists(List<String> tagNameList);

    List<Tag> saveTagIfNotExists(List<String> tagNameList);

    Optional<Tag> findTagByName(String name);
}
