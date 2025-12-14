package ru.yp.sprint5pw.service;

import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.Product;

public interface CartService {
    Cart getCartByUserId(Integer userId);
}
