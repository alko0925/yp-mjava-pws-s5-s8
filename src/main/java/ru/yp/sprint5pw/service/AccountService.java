package ru.yp.sprint5pw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yp.sprint5pw.entity.Account;
import ru.yp.sprint5pw.repository.AccountDao;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    public void transfer(Account source, Account target, BigDecimal amount) {
        // Увеличиваем баланс получателя и сохраняем
        target.setBalance(target.getBalance().add(amount));
        accountDao.update(target);

        // Уменьшаем баланс отправителя и сохраняем
        source.setBalance(source.getBalance().subtract(amount));
        accountDao.update(source);
    }
}
