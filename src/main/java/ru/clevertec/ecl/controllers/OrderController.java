package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.services.OrderService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    ResponseEntity<OrderDto> createOrder(@Positive Long userId, Long certificateId) {
        return new ResponseEntity<>(orderService.createOrder(userId, certificateId),
                HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<OrderDto>> findOrdersByUser(@Positive Long userId, PageRequest pageRequest) {
        return new ResponseEntity<>(orderService.findOrderListByUser(userId, pageRequest),
                HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderInformationDto> findOrderByUser(@Positive Long userId, @PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.findOrderByUser(userId, orderId),
                HttpStatus.OK);
    }
}
