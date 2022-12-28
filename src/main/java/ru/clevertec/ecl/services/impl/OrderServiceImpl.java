package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityService<GiftCertificate> giftCertificateService;
    private final EntityService<User> userService;
    private final OrderMapper mapper;

    @Transactional
    public OrderDto createOrder(Long userId, Long certificateId) {
        GiftCertificate giftCertificate = giftCertificateService.findById(certificateId);
        User user = userService.findById(userId);
        Order order = Order.builder()
                .user(user)
                .giftCertificate(giftCertificate)
                .price(giftCertificate.getPrice())
                .build();
        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> findOrderListByUser(Long userId, PageRequest page) {
        return mapper.toDtoList(userService.findById(userId).getOrders());
    }

    @Override
    public OrderInformationDto findOrderByUser(Long userId, Long orderId) {
        return mapper.toInformationDto(orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(Order.class, orderId)));
    }
}
