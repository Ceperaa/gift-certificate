package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

public interface TagCreateCertificate {

    Tag saveTag(Tag tag);

    List<Tag> mapToTagList(List<String> tagNameList);
}
