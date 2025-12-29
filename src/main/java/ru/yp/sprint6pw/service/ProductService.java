package ru.yp.sprint6pw.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.controller.dto.Page;
import ru.yp.sprint6pw.model.Product;

import java.util.List;

public interface ProductService {
    Flux<Product> getAllProducts();
    Mono<Page> getProducts(String search, String sort, Integer pageNumber, Integer pageSize);
    Mono<Product> getProduct(Integer itemId);
//    Product create(Product product);
//    void update(Product product);
}
