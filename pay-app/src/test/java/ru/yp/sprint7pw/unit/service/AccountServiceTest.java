package ru.yp.sprint7pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.PayApplicationTest;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.AccountOperationRepository;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;
import ru.yp.sprint7pw.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class AccountServiceTest extends PayApplicationTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AccountRepository accountRepository;

    @MockitoBean
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void resetMocks() {
        reset(userRepository, accountRepository, accountOperationRepository);
    }

    @Test
    void testGetBalanceByUserId() {
        Integer userId = 1;
        Integer accountId = 2;
        Double balance = 10000D;

        User user = new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com");
        user.setId(userId);

        Account account = new Account(userId, balance);
        account.setId(accountId);

        doReturn(Mono.just(user)).when(userRepository).findById(userId);
        doReturn(Mono.just(account)).when(accountRepository).findById(user.getId());

        Double result = accountService.getBalanceByUserId(userId).block();

        assertNotNull(result, "Balance should not be null");
        assertEquals(balance, result, "Wrong balance was retrieved");
        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).findById(user.getId());
    }

    @Test
    void testGetAccountByUserId() {
        Integer userId = 1;
        Integer accountId = 2;
        Double balance = 10000D;

        User user = new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com");
        user.setId(userId);

        Account account = new Account(userId, balance);
        account.setId(accountId);

        doReturn(Mono.just(user)).when(userRepository).findById(userId);
        doReturn(Mono.just(account)).when(accountRepository).findById(user.getId());

        Account result = accountService.getAccountByUserId(userId).block();

        assertNotNull(result, "Account should not be null");
        assertEquals(accountId, result.getId(), "Account with wrong id was retrieved");
        assertEquals(userId, result.getUserId(), "Account for wrong user was retrieved");
        assertEquals(balance, result.getBalance(), "Account with wrong balance was retrieved");
        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).findById(user.getId());
    }

    @Test
    void testUpdateBalanceByUserId() {
        Integer userId = 1;
        Integer accountId = 2;
        Double balance = 10000D;

        String operation = "deposit";
        Double amount = 1000D;

        User user = new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com");
        user.setId(userId);

        Account account = new Account(userId, balance);
        account.setId(accountId);

        doReturn(Mono.just(user)).when(userRepository).findById(userId);
        doReturn(Mono.just(account)).when(accountRepository).findById(user.getId());
        doReturn(Mono.empty()).when(accountOperationRepository).save(Mockito.any());
        doReturn(Mono.just(account)).when(accountRepository).save(account);

        Double result = accountService.updateBalanceByUserId(userId, operation, amount).block();

        assertNotNull(result, "Balance should not be null");
        assertEquals(balance+amount, result, "Wrong balance was retrieved");
        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).findById(user.getId());
        verify(accountOperationRepository, times(1)).save(Mockito.any());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testApplyOperationToAccount() {
        Integer userId = 1;
        Integer accountId = 2;
        Double balance = 10000D;

        String operation = "payment";
        Double amount = 1000D;

        Account account = new Account(userId, balance);
        account.setId(accountId);

        doReturn(Mono.empty()).when(accountOperationRepository).save(Mockito.any());
        doReturn(Mono.just(account)).when(accountRepository).save(account);

        Account result = accountService.applyOperationToAccount(account, operation, amount).block();

        assertNotNull(result, "Account should not be null");
        assertEquals(accountId, result.getId(), "Account with wrong id was retrieved");
        assertEquals(userId, result.getUserId(), "Account for wrong user was retrieved");
        assertEquals(balance-amount, result.getBalance(), "Wrong balance was retrieved");
        verify(accountOperationRepository, times(1)).save(Mockito.any());
        verify(accountRepository, times(1)).save(account);
    }
}
