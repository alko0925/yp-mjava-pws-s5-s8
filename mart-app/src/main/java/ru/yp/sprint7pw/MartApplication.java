package ru.yp.sprint7pw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yp.sprint7pw.client.ApiClient;
import ru.yp.sprint7pw.client.api.AccountApi;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.ProductRepository;

import java.util.List;

@SpringBootApplication
public class MartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MartApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            productRepository.saveAll(List.of(new Product("Курочка", "Всегда оповестит команду о начале очередного Scrum дейлика.", "image/1s.jpg", 7000L),
                    new Product("Нимбус 2000", "Это тебе не Хоббихорсинг.", "image/2s.jpg", 1000000L),
                    new Product("Подкрадули", "Стильно, модно, молодежно.", "image/3s.jpg", 70000L),
                    new Product("Ковер", "Кто знает тот поймет.", "image/4s.jpg", 10000L),
                    new Product("Маска Коня", "Хочешь удивить друзей? Или просто быть в режиме инкогнито? Легко!", "image/5s.jpg", 8000L),
                    new Product("Оберег", "Значительно повышает навык коммуникации.", "image/6s.jpg", 10000L),
                    new Product("Баян", "Баян.", "image/7s.jpg", 150000L),
                    new Product("Пуш девайс", "Незаменимый тул когда подгорает дедлайн.", "image/8s.jpg", 5000L),
                    new Product("ДебагДаг", "Всегда подскажет верное решение.", "image/9s.jpg", 15000L)
            )).subscribe();
        };
    }

    @Bean
    ApiClient apiClient() {
        return new ApiClient();
    }

    @Bean
    AccountApi accountApi(ApiClient apiClient) {
        return new AccountApi(apiClient);
    }
}
