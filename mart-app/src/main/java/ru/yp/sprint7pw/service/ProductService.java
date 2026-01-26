package ru.yp.sprint7pw.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.Page;
import ru.yp.sprint7pw.model.Product;

public interface ProductService {
    Flux<Product> getAllProducts();
    Mono<Page> getProducts(String search, String sort, Integer pageNumber, Integer pageSize);
    Mono<Product> getProduct(Integer itemId);
    Mono<Product> create(Product product);
    Mono<Product> update(Product product);
}
