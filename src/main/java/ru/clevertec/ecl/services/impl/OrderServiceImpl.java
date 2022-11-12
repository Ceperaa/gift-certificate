package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.dto.OrderCommitLogDto;
import ru.clevertec.ecl.model.dto.OrderCreateDto;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.OrderService;

import java.util.List;

import static ru.clevertec.ecl.util.Constant.ORDER_SEQ;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService, EntityService<Order> {

    private final OrderRepository orderRepository;
    private final EntityService<GiftCertificate> giftCertificateService;
    private final EntityService<User> userService;
    private final OrderMapper mapper;

    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        GiftCertificate giftCertificate = giftCertificateService.findById(orderCreateDto.getGiftCertificateId());
        User user = userService.findById(orderCreateDto.getUserId());
        Order order = Order.builder()
                .id(orderCreateDto.getId())
                .user(user)
                .giftCertificate(giftCertificate)
                .price(giftCertificate.getPrice())
                .build();
        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> findOrderListByUser(Long userId, Pageable page) {
        return mapper.toDtoList(userService.findById(userId).getOrders());
    }

    @Override
    public OrderInformationDto findOrderByUser(Long userId, Long orderId) {
        return mapper.toInformationDto(orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(Order.class, orderId)));
    }

    @Override
    public Order findById(Long id) {
        return orderRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
    }

    @Override
    public OrderCommitLogDto findOrderCommitLogById(Long id) {
        return mapper.toCommitLogDto(findById(id));
    }

    @Override
    public Long getSequence() {
        return orderRepository.getValMySequence();
    }

    @Override
    @Transactional
    public OrderDto saveRecovery(Order order) {
        orderRepository.setval(ORDER_SEQ, order.getId());
        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDto saveDto(Order order) {
        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<Order> findByFirstIdAndLastId(Long firstId, Long lastId) {
        return orderRepository.findByIdBetween(firstId, lastId);
    }

    @Override
    @Transactional
    public Long nextval() {
        return orderRepository.nextval(ORDER_SEQ);
    }
}
