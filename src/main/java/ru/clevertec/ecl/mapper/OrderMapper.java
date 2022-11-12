package ru.clevertec.ecl.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.OrderCommitLogDto;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.Order;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(Order order);

    OrderCommitLogDto toCommitLogDto(Order order);

    Order toEntity(OrderCommitLogDto orderCommitLogDto);

    Order toEntity(OrderDto order);

    List<OrderDto> toDtoList(List<Order> orders);

    OrderInformationDto toInformationDto(Order order);
}
