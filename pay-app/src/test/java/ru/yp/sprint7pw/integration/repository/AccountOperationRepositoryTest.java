package ru.yp.sprint7pw.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint7pw.PayApplicationTest;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.AccountOperation;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.AccountOperationRepository;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountOperationRepositoryTest extends PayApplicationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @BeforeEach
    void setUp() {
        accountOperationRepository.deleteAll()
                .then(accountRepository.deleteAll())
                .then(userRepository.deleteAll())
                .then(userRepository.save(new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com")))
                .flatMap(user -> accountRepository.save(new Account(user.getId(), 10000D)))
                .flatMap(account -> {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setAccountId(account.getId());
                    accountOperation.setOperation("deposite");
                    accountOperation.setAmount(1000D);
                    accountOperation.setOriginalBalance(account.getBalance());
                    accountOperation.setNewBalance(account.getBalance() + 1000D);
                    return accountOperationRepository.save(accountOperation);
                })
                .block();
    }

    @Test
    void testFindAll() {
        String operation = "deposite";
        Double balance = 11000D;
        List<AccountOperation> result = accountOperationRepository.findAll().collectList().block();

        assertNotNull(result, "AccountOperation list should not be empty");
        assertEquals(1, result.size(), "AccountOperation list must contain only one entity");
        assertEquals(balance, result.getFirst().getNewBalance(), "AccountOperation entity has wrong new balance value");
        assertEquals(operation, result.getFirst().getOperation(), "AccountOperation entity has wrong operation value");
    }

    @AfterEach
    void cleanUp() {
        accountOperationRepository.deleteAll()
                .then(accountRepository.deleteAll())
                .then(userRepository.deleteAll())
                .block();
    }
}
