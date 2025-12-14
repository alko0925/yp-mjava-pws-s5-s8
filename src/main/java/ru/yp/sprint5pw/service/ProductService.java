package ru.yp.sprint5pw.service;

import ru.yp.sprint5pw.controller.dto.PageParams;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.Order;
import ru.yp.sprint5pw.model.Product;

import java.util.List;

public interface ProductService {
    Iterable<Product> getAllProducts();
    List<Product> getProducts(String search, String sort, PageParams pageParams);
}
