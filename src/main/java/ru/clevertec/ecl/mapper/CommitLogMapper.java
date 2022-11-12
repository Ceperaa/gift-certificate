package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.http.HttpMethod;
import ru.clevertec.ecl.model.entity.CommitLog;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TagMapper.class)
public interface CommitLogMapper {

    @Mapping(source = "method", target = "method")
    @Mapping(source = "entityName", target = "entityName")
    @Mapping(source = "sequence", target = "sequence")
    CommitLog toDtoList(Long sequence, String entityName, HttpMethod method);
}
