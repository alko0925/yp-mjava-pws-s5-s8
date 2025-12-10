package ru.yp.sprint5pw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.yp.sprint5pw.entity.Account;
import ru.yp.sprint5pw.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class TransactionTemplateService {
    private final TransactionTemplate transactionTemplate;
    private final AccountRepository accountRepository;

    public TransactionTemplateService(
            AccountRepository accountRepository,
            PlatformTransactionManager transactionManager
    ) {
        this.accountRepository = accountRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public void transfer(Account source, Account target, BigDecimal amount) {
        // Открываем транзакцию
        transactionTemplate.executeWithoutResult(status -> {
            // Увеличиваем баланс получателя и сохраняем
            target.setBalance(target.getBalance().add(amount));
            accountRepository.save(target);

            // Уменьшаем баланс отправителя и сохраняем
            source.setBalance(source.getBalance().subtract(amount));
            accountRepository.save(source);
        }); // При успешном выполнении лямбды — commit. При ошибках — rollback
    }
}