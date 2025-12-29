package ru.yp.sprint6pw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.CartProduct;
import ru.yp.sprint6pw.repository.CartProductRepository;
import ru.yp.sprint6pw.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

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
    public Mono<Cart> getCartByUserId(Integer userId) {

        Mono<Cart> cart = cartRepository.findCartByUserId(userId)
                .defaultIfEmpty(new Cart());

        Mono<List<CartProduct>> cartProducts = cart.flatMap(c -> {
            if (c.getId() != null) return cartProductRepository.findAllById(List.of(c.getId())).collectList();
            else return Mono.just(new ArrayList<CartProduct>());
        });

        return Mono.zip(cart, cartProducts)
                .map(tuple -> {
                    Cart c = tuple.getT1();
                    c.setCartProducts(tuple.getT2());
                    return c;
                });
    }

//    @Override
//    public void decreaseProductCount(Integer userId, Product product) {
//        Cart cart = cartRepository.findCartByUserId(userId);
//        if (cart == null) {
//            return;
//        }
//
//        CartProduct cartProduct = cart.getCartProducts().stream()
//                .filter(cp -> cp.getProduct().getId().equals(product.getId()))
//                .findFirst().orElse(null);
//
//        if (cartProduct == null) {
//            return;
//        } else {
//            if (cartProduct.getQuantity() == 1) {
//                cartProductRepository.delete(cartProduct);
//                cart.getCartProducts().remove(cartProduct);
//            } else {
//                cartProduct.setQuantity(cartProduct.getQuantity() - 1);
//                cartProductRepository.save(cartProduct);
//            }
//        }
//
//        if (cart.getCartProducts().isEmpty()) cartRepository.delete(cart);
//        else cartRepository.save(cart);
//    }

//    @Override
//    public void increaseProductCount(Integer userId, Product product) {
//        Cart cart = cartRepository.findCartByUserId(1);
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUserId(1);
//            cart = cartRepository.save(cart);
//        }
//
//        CartProduct cartProduct = cart.getCartProducts().stream()
//                .filter(cp -> cp.getProduct().getId().equals(product.getId()))
//                .findFirst().orElse(null);
//
//        if (cartProduct == null) {
//            cartProduct = new CartProduct(cart, product, 1);
//            cartProductRepository.save(cartProduct);
//            cart.getCartProducts().add(cartProduct);
//        } else {
//            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
//            cartProductRepository.save(cartProduct);
//        }
//
//        cartRepository.save(cart);
//    }

//    @Override
//    public void deleteProduct(Integer userId, Product product) {
//        Cart cart = cartRepository.findCartByUserId(userId);
//        if (cart == null) {
//            return;
//        }
//
//        CartProduct cartProduct = cart.getCartProducts().stream()
//                .filter(cp -> cp.getProduct().getId().equals(product.getId()))
//                .findFirst().orElse(null);
//
//        if (cartProduct == null) {
//            return;
//        } else {
//            cartProductRepository.delete(cartProduct);
//            cart.getCartProducts().remove(cartProduct);
//        }
//
//        if (cart.getCartProducts().isEmpty()) cartRepository.delete(cart);
//        else cartRepository.save(cart);
//    }

//    @Override
//    public void delete(Cart cart) {
//        cartRepository.delete(cart);
//    }
}
