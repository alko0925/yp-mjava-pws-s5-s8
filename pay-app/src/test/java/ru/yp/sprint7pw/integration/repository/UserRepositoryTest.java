package ru.yp.sprint7pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint7pw.PayApplicationTest;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRepositoryTest extends PayApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll()
                .then(userRepository.save(new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com")))
                .block();
    }

    @Test
    void testFindAll() {
        List<User> result = userRepository.findAll().collectList().block();

        assertNotNull(result, "User list should not be empty");
        assertEquals(1, result.size(), "User list must contain only one entity");
        assertEquals("Ivan", result.getFirst().getFirstName(), "Wrong User was retrieved");
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll()
                .block();
    }
}
