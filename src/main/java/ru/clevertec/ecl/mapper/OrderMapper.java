package ru.clevertec.ecl.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.Order;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    OrderInformationDto toInformationDto(Order order);
}
