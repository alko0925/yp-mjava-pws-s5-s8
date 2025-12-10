package ru.yp.sprint5pw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
