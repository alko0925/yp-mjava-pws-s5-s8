package ru.yp.sprint5pw.service;

import ru.yp.sprint5pw.controller.dto.PageParams;
import ru.yp.sprint5pw.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProducts(String search, String sort, PageParams pageParams);
    Product getProduct(Integer itemId);
    Product create(Product product);
    void update(Product product);
}
