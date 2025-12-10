package ru.yp.sprint5pw.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yp.sprint5pw.MyMarketApplicationTest;
import ru.yp.sprint5pw.entity.Account;
import ru.yp.sprint5pw.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CrudOperationsTest extends MyMarketApplicationTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testCreate() {
        var anatoly = accountRepository.save(new Account("Анатолий"));

        assertThat(anatoly)
            .isNotNull()
            .withFailMessage("Созданной записи должен был быть присвоен ID")
            .extracting(Account::getId)
            .isNotNull();
    }

    @Test
    public void testDelete() {
        var mariana = accountRepository.save(new Account("Мариана"));
        accountRepository.delete(mariana);

        assertThat(accountRepository.existsById(mariana.getId()))
            .withFailMessage("Удаленная запись не должна быть найдена")
            .isFalse();
    }
}
