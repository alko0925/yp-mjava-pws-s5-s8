package ru.yp.sprint7pw.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yp.sprint7pw.model.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
}
