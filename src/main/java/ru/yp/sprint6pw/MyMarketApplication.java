package ru.yp.sprint6pw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.repository.ProductRepository;

@SpringBootApplication
public class MyMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMarketApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(ProductRepository productRepository) {
//		return args -> {
//			productRepository.save(new Product("Курочка", "Всегда оповестит команду о начале очередного Scrum дейлика.", "image/1s.jpg", 7000L));
//			productRepository.save(new Product("Нимбус 2000", "Это тебе не Хоббихорсинг.", "image/2s.jpg", 1000000L));
//			productRepository.save(new Product("Подкрадули", "Стильно, модно, молодежно.", "image/3s.jpg", 70000L));
//			productRepository.save(new Product("Ковер", "Кто знает тот поймет.", "image/4s.jpg", 10000L));
//			productRepository.save(new Product("Маска Коня", "Хочешь удивить друзей? Или просто быть в режиме инкогнито? Легко!", "image/5s.jpg", 8000L));
//			productRepository.save(new Product("Оберег", "Значительно повышает навык коммуникации.", "image/6s.jpg", 10000L));
//			productRepository.save(new Product("Баян", "Баян.", "image/7s.jpg", 150000L));
//			productRepository.save(new Product("Пуш дейвайс", "Незаменимый тул когда подгорает дедлайн.", "image/8s.jpg", 5000L));
//			productRepository.save(new Product("ДебагДаг", "Всегда подскажет верное решение.", "image/9s.jpg", 15000L));
//		};
//	}
}
