package ru.clevertec.ecl.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.OrderCommitLogDto;
import ru.clevertec.ecl.model.dto.OrderCreateDto;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.OrderService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("v1/orders")
@Validated
@Slf4j
public class OrderController extends AbstractController<Order> {

    private final OrderService orderService;

    public OrderController(EntityService<Order> entityService, OrderService orderService) {
        super(entityService);
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(orderService.createOrder(orderCreateDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<OrderDto>> findOrdersByUser(@Positive @RequestParam Long userId,
                                                    Pageable pageRequest) {
        return new ResponseEntity<>(orderService.findOrderListByUser(userId, pageRequest),
                HttpStatus.OK);
    }

    @GetMapping("{id}/recovery")
    ResponseEntity<OrderCommitLogDto> findOrdersById(@Positive @PathVariable Long id) {
        return new ResponseEntity<>(orderService.findOrderCommitLogById(id),
                HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderInformationDto> findOrderByUser(@Positive @RequestParam Long userId,
                                                        @PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.findOrderByUser(userId, orderId),
                HttpStatus.OK);
    }

    @PostMapping("/recovery")
    @ApiIgnore
    public ResponseEntity<OrderDto> recovery(@RequestBody @Valid Order order) {
        log.debug("Order - adding");
        return new ResponseEntity<>(orderService.saveRecovery(order),
                HttpStatus.CREATED);
    }

    @GetMapping("/sequence")
    @ApiIgnore
    public Long getSequence() {
        return orderService.getSequence();
    }
}
