package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.yp.sprint7pw.PayApplicationTest;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.AccountOperationRepository;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;

public class PayApplicationWebTest extends PayApplicationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected AccountOperationRepository accountOperationRepository;

    @BeforeEach
    void setup() {
        userRepository.save(new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com"))
                .flatMap(user -> accountRepository.save(new Account(user.getId(), 10000D)))
                .block();
    }

    @AfterEach
    void cleanUp() {
        accountRepository.deleteAll()
                .then(userRepository.deleteAll())
                .block();
    }
}