package ru.yp.sprint7pw.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.exception.ApiException;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.AccountOperation;
import ru.yp.sprint7pw.repository.AccountOperationRepository;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository, AccountOperationRepository accountOperationRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @Override
    public Mono<Double> getBalanceByUserId(Integer userId) {
        return getAccountByUserId(userId)
                .map(Account::getBalance);
    }

    @Override
    public Mono<Account> getAccountByUserId(Integer userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ApiException(HttpStatus.NOT_FOUND, "User: " + userId + " not found")))
                .flatMap(user -> accountRepository.findById(user.getId()))
                .switchIfEmpty(Mono.error(new ApiException(HttpStatus.NOT_FOUND, "Account for User: " + userId + " not found")));
    }

    @Override
    public Mono<Double> updateBalanceByUserId(Integer userId, String operation, Double amount) {
        return getAccountByUserId(userId)
                .flatMap(account -> applyOperationToAccount(account, operation, amount))
                .map(Account::getBalance);
    }

    @Override
    public Mono<Account> applyOperationToAccount(Account account, String operation, Double amount) {
        if (!ServiceConstants.OperationType.isEnumContains(operation.toUpperCase()))
            return Mono.error(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Operation: " + operation + " is not supported"));

        if (account.getBalance() - amount < 0)
            return Mono.error(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient funds in the balance. Current balance: " + account.getBalance() + ". Required: " + amount + "."));

        if (amount <= 0)
            return Mono.error(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Operation amount should be positive. Operation: " + operation + ". Amount: " + amount + "."));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccountId(account.getId());
        accountOperation.setOperation(operation);
        accountOperation.setAmount(amount);
        accountOperation.setOriginalBalance(account.getBalance());

        switch (ServiceConstants.OperationType.valueOf(operation.toUpperCase())) {
            case DEPOSIT -> {
                account.setBalance(account.getBalance() + amount);
                accountOperation.setNewBalance(account.getBalance() + amount);
            }
            case PAYMENT -> {
                account.setBalance(account.getBalance() - amount);
                accountOperation.setNewBalance(account.getBalance() - amount);
            }
        }

        return accountOperationRepository.save(accountOperation)
                .then(accountRepository.save(account));
    }
}
