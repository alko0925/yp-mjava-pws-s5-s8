package ru.yp.sprint5pw.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.repository.CartProductRepository;
import ru.yp.sprint5pw.repository.CartRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public CartServiceImpl(CartRepository cartRepository, CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Override
    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findCartByUserId(userId);
    }
}
