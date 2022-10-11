package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testFindByMaxSaleAngMaxUseTag() {
        UserMaxSaleDto byMaxSaleAngMaxUseTag = userRepository.findByMaxSaleAngMaxUseTag();
        assertEquals(byMaxSaleAngMaxUseTag.getName(), null);
    }
}