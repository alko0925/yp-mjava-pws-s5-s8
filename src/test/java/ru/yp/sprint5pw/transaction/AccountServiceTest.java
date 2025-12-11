package ru.yp.sprint5pw.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.yp.sprint5pw.MyMarketApplicationTest;
import ru.yp.sprint5pw.entity.Account;
import ru.yp.sprint5pw.repository.AccountDao;
import ru.yp.sprint5pw.service.AccountService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest extends MyMarketApplicationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDao accountDao;

    @Test
    void testSuccessfullSqlQueries() {
        // Инициализируем пользователей (изначальный баланс — 10000)
        accountDao.create("Пётр");
        accountDao.create("Василий");
        var petrAccount = accountDao.findByName("Пётр");
        var vasilyAccount = accountDao.findByName("Василий");
        var initialBalance = petrAccount.getBalance();

        // Переводим от Василия Петру 100000 (возникает ошибка ограничения на баланс)
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> accountService.transfer(vasilyAccount, petrAccount, BigDecimal.valueOf(100_000L))
        );

        // Проверяем, что транзакция откатилась
        // Не должно возникнуть ситуации, что Петру деньги начислились, а с Василия не списались
        assertThat(accountDao.findAll())
                .isNotEmpty()
                .withFailMessage("При возникновении ошибки во время транзакции " +
                        "балансы обоих пользователей должны вернуться к изначальным значениям")
                .map(Account::getBalance)
                .allMatch(it -> it.compareTo(initialBalance) == 0);
    }
}
