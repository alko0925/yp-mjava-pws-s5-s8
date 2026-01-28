package ru.yp.sprint7pw.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint7pw.model.Account;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Integer> {
}