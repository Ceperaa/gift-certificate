package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Component
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;


    @Test
    @Transactional
    void findByNameWithPagination() {
        List<GiftCertificate> byNameWithPagination = giftCertificateRepository
                .findByNameWithPagination("", PageRequest.of(0, 20));
        assertEquals(byNameWithPagination.size(), 8);
    }
}