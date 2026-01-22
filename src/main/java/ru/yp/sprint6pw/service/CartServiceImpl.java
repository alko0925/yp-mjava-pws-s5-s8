package ru.yp.sprint6pw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.CartProduct;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.repository.CartProductRepository;
import ru.yp.sprint6pw.repository.CartRepository;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.productService = productService;
    }

    @Override
    public Mono<Cart> getCartByUserId(Integer userId) {

        Mono<Cart> cart = cartRepository.findCartByUserId(userId)
                .defaultIfEmpty(new Cart());

        Mono<List<CartProduct>> cartProducts = cart.flatMapMany(c -> {
            if (c.getId() != null) return cartProductRepository.findAllById(List.of(c.getId()));
            else return Flux.empty();
        }).flatMap(this::setProductToCartProduct).collectList();

        return Mono.zip(cart, cartProducts)
                .map(tuple -> {
                    Cart c = tuple.getT1();
                    c.setCartProducts(tuple.getT2());
                    return c;
                });
    }

    @Override
    public Mono<CartProduct> setProductToCartProduct(CartProduct cartProduct) {
        return productService.getProduct(cartProduct.getProductId())
                .map(p -> {
                    cartProduct.setProduct(p);
                    return cartProduct;
                });
    }

    @Override
    public Mono<Void> decreaseProductCount(Integer userId, Product product) {

        return getCartByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getId() != null) {
                        CartProduct cartProduct = cart.getCartProductByProduct(product);
                        if (cartProduct == null) {
                            return Mono.empty();
                        } else {
                            if (cartProduct.getQuantity() == 1)
                                return cartProductRepository.deleteByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId());
                            else
                                return cartProductRepository.saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity() - 1));
                        }
                    } else return Mono.empty();
                })
                .then(getCartByUserId(userId))
                .flatMap(cart -> {
                            if (cart.getId() != null && cart.getCartProducts().isEmpty()) return cartRepository.delete(cart);
                            else return Mono.empty();
                        }
                );
    }

    @Override
    public Mono<Void> increaseProductCount(Integer userId, Product product) {

        return getCartByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getId() == null) {
                        cart.setUserId(userId);
                        return cartRepository.save(cart);
                    } else return Mono.empty();
                })
                .then(getCartByUserId(userId))
                .flatMap(cart -> {
                    if (cart.getId() != null) {
                        CartProduct cartProduct = cart.getCartProductByProduct(product);
                        if (cartProduct == null)
                            return cartProductRepository.saveByCartIdAndProductId(cart.getId(), product.getId());
                        else
                            return cartProductRepository.saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity() + 1));

                    } else return Mono.empty();
                }).then();
    }

    @Override
    public Mono<Void> deleteProduct(Integer userId, Product product) {

        return getCartByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getId() != null) {
                        CartProduct cartProduct = cart.getCartProductByProduct(product);
                        if (cartProduct == null)
                            return Mono.empty();
                        else
                            return cartProductRepository.deleteByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId());

                    } else return Mono.empty();
                })
                .then(getCartByUserId(userId))
                .flatMap(cart -> {
                            if (cart.getId() != null && cart.getCartProducts().isEmpty()) return cartRepository.delete(cart);
                            else return Mono.empty();
                        }
                );
    }

    @Override
    public Mono<Void> delete(Cart cart) {
        return cartRepository.delete(cart);
    }
}
