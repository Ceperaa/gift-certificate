package ru.clevertec.ecl.services;


import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.OrderCommitLogDto;
import ru.clevertec.ecl.model.dto.OrderCreateDto;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.Order;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderCreateDto orderCreateDto);

    List<OrderDto> findOrderListByUser(Long userId, Pageable page);

    OrderInformationDto findOrderByUser(Long userId, Long orderId);

    Long getSequence();

    OrderDto saveRecovery(Order user);

    OrderDto saveDto(Order user);

    OrderCommitLogDto findOrderCommitLogById(Long id);
}
