package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // todo  Разбиение на страницы должно быть реализовано для всех конечных точек GET.
    //         Пожалуйста, создайте гибкое и безошибочное решение. Обработка всех исключительных случаев.

    //  todo  3. Сделать заказ подарочного сертификата для пользователя (у пользователя должна быть возможность купить сертификат).
    @PostMapping
    ResponseEntity<OrderDto> createOrder(Long userId, Long certificateId) {
        return new ResponseEntity<>(orderService.createOrder(userId, certificateId), HttpStatus.CREATED);
    }

    // todo  4. Получить информацию о заказах пользователя.
    @GetMapping
    ResponseEntity<List<OrderDto>> findOrdersByUser(Long userId, Integer limit, Integer offset){
        return new ResponseEntity<>(orderService.findOrdersByUser(userId, PageRequest.of(offset,limit)),HttpStatus.OK);
    }

    //  todo  5. Получить информацию о заказе пользователя: стоимость и время покупки.
    //         *Стоимость заказа не должна изменяться при изменении цены подарочного сертификата.
    @GetMapping("/{orderId}")
    ResponseEntity<OrderInformationDto> findOrderByUser(Long userId, @PathVariable Long orderId){
        return new ResponseEntity<>(orderService.findOrderByUser(userId,orderId),HttpStatus.OK);
    }
}
