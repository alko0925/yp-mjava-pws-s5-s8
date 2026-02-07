package ru.yp.sprint7pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint7pw.PayApplicationTest;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountRepositoryTest extends PayApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll()
                .then(userRepository.deleteAll())
                .then(userRepository.save(new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com")))
                .flatMap(user -> accountRepository.save(new Account(user.getId(), 10000D)))
                .block();
    }

    @Test
    void testFindAll() {
        Double balance = 10000D;
        List<Account> result = accountRepository.findAll().collectList().block();

        assertNotNull(result, "Account list should not be empty");
        assertEquals(1, result.size(), "Account list must contain only one entity");
        assertEquals(balance, result.getFirst().getBalance(), "Wrong Account balance was retrieved");
    }

    @AfterEach
    void cleanUp() {
        accountRepository.deleteAll()
                .then(userRepository.deleteAll())
                .block();
    }
}
