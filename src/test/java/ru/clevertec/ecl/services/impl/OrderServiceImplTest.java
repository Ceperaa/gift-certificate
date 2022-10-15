package ru.clevertec.ecl.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.OrderService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class OrderServiceImplTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper mapper;
    private final OrderService orderService;

    @Mock
    private EntityService<GiftCertificate> giftCertificateService;

    @Mock
    private EntityService<User> userService;

    private Order orders;
    private OrderDto orderDto;
    private OrderInformationDto orderInformationDto;
    private User users;
    private GiftCertificate giftCertificate;

    OrderServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.orderService = new OrderServiceImpl(repository, giftCertificateService, userService, mapper);
    }

    @BeforeEach
    void setUp() {
        orders = ObjectSupplier.getOrder();
        orderDto = ObjectSupplier.getOrderDto();
        orderInformationDto = ObjectSupplier.getOrderInformationDto();
        users = ObjectSupplier.getUser();
        giftCertificate = ObjectSupplier.getGiftCertificate();
    }

    @Test
    void createOrder() {
        given(mapper.toDto(orders)).willReturn(orderDto);
        given(giftCertificateService.findById(1L)).willReturn(giftCertificate);
        given(userService.findById(1L)).willReturn(users);
        given(repository.save(ArgumentMatchers.any())).willReturn(orders);
        OrderDto order = orderService.createOrder(1L, 1L);
        assertEquals(order, orderDto);
    }

    @Test
    void findOrdersByUser() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<Order> ordersList = List.of(this.orders);
        List<OrderDto> orderDtoList = List.of(this.orderDto);
        given(userService.findById(1L)).willReturn(users);
        given(mapper.toDtoList(ordersList)).willReturn(orderDtoList);
        List<OrderDto> orderListByUser = orderService.findOrderListByUser(1L, pageRequest);
        assertEquals(orderListByUser, orderDtoList);
    }

    @Test
    void findOrderByUser() {
        given(repository.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(orders));
        given(mapper.toInformationDto(orders)).willReturn(orderInformationDto);
        OrderInformationDto orderByUser = orderService.findOrderByUser(1L, 1L);
        assertEquals(orderByUser, orderInformationDto);
    }
}