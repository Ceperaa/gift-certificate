package ru.clevertec.ecl.services;


import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(Long userId, Long certificateId);

    List<OrderDto> findOrderListByUser(Long userId, PageRequest page);

    OrderInformationDto findOrderByUser(Long userId, Long orderId);
}
