package ru.yp.sprint5pw.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.CartProduct;
import ru.yp.sprint5pw.model.Product;
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

    @Override
    public void decreaseProductCount(Integer userId, Product product) {
        Cart cart = cartRepository.findCartByUserId(userId);
        if (cart == null) {
            return;
        }

        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (cartProduct == null) {
            return;
        } else {
            if (cartProduct.getQuantity() == 1) {
                cartProductRepository.delete(cartProduct);
                cart.getCartProducts().remove(cartProduct);
            } else {
                cartProduct.setQuantity(cartProduct.getQuantity() - 1);
                cartProductRepository.save(cartProduct);
            }
        }

        if (cart.getCartProducts().isEmpty()) cartRepository.delete(cart);
        else cartRepository.save(cart);
    }

    @Override
    public void increaseProductCount(Integer userId, Product product) {
        Cart cart = cartRepository.findCartByUserId(1);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(1);
            cart = cartRepository.save(cart);
        }

        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (cartProduct == null) {
            cartProduct = new CartProduct(cart, product, 1);
            cartProductRepository.save(cartProduct);
            cart.getCartProducts().add(cartProduct);
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
            cartProductRepository.save(cartProduct);
        }

        cartRepository.save(cart);
    }
}
