package ru.yp.sprint7pw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yp.sprint7pw.model.Account;
import ru.yp.sprint7pw.model.User;
import ru.yp.sprint7pw.repository.AccountRepository;
import ru.yp.sprint7pw.repository.UserRepository;


@SpringBootApplication
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepository, AccountRepository accountRepository) {
        return args -> {
            userRepository.save(new User("Ivan", "Ivanov", "ivan.ivanov@payapp.com"))
                    .flatMap(user -> accountRepository.save(new Account(user.getId(), 10000D)))
                    .subscribe();
        };
    }
}
