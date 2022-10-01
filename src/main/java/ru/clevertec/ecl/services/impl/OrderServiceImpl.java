package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.OrderInformationDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Orders;
import ru.clevertec.ecl.model.entity.Users;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityService<GiftCertificate> giftCertificateService;
    private final EntityService<Users> userService;
    private final OrderMapper mapper;

    // TODO: 30.09.2022 3. Сделать заказ подарочного сертификата для пользователя (у пользователя должна быть возможность купить сертификат).
    @Override
    public OrderDto createOrder(Long userId, Long certificateId) {
        GiftCertificate giftCertificate = giftCertificateService.findById(certificateId);
        Users user = userService.findById(userId);
        Orders order = Orders.builder()
                .createDate(LocalDateTime.now())
                .user(user)
                .giftCertificate(giftCertificate)
                .price(giftCertificate.getPrice())
                .build();
        return mapper.toDto(orderRepository.save(order));
    }

    // TODO: 30.09.2022 4. Получить информацию о заказах пользователя.
    @Override
    public List<OrderDto> findOrdersByUser(Long userId, PageRequest page) {
        return mapper.toDtoList(userService.findById(userId).getOrders());
    }

    // TODO: 30.09.2022 5. Получить информацию о заказе пользователя: стоимость и время покупки.
    //         *Стоимость заказа не должна изменяться при изменении цены подарочного сертификата.
    @Override
    public OrderInformationDto findOrderByUser(Long userId, Long orderId) {
        return mapper.toInformationDto(orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ObjectNotFoundException(Orders.class,orderId)));
    }

}
