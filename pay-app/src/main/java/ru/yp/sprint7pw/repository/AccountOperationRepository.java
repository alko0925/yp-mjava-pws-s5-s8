package ru.yp.sprint7pw.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint7pw.model.AccountOperation;

import java.math.BigInteger;

@Repository
public interface AccountOperationRepository extends ReactiveCrudRepository<AccountOperation, BigInteger> {
}