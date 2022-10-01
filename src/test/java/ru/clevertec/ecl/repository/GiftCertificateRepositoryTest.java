package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
class GiftCertificateRepositoryTest {

    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    GiftCertificateRepositoryTest(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Test
    @Transactional
    void findByNameWithPagination() {
        List<GiftCertificate> byNameWithPagination = giftCertificateRepository
                .findByName("", PageRequest.of(0, 20));
        assertEquals(byNameWithPagination.size(), 8);
    }
}