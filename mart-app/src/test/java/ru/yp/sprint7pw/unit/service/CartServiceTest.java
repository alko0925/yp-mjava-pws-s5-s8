package ru.yp.sprint7pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.MartApplicationTest;
import ru.yp.sprint7pw.model.Cart;
import ru.yp.sprint7pw.model.CartProduct;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.repository.CartProductRepository;
import ru.yp.sprint7pw.repository.CartRepository;
import ru.yp.sprint7pw.service.CartService;
import ru.yp.sprint7pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartServiceTest extends MartApplicationTest {

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private CartProductRepository cartProductRepository;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void resetMocks() {
        reset(cartRepository, cartProductRepository);
    }

    @Test
    void testGetCartByUserId() {
        Integer userId = 1;
        Integer cartId = 2;
        Integer productId = 3;
        Integer quantity = 4;

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);

        Product product = new Product();
        product.setId(productId);

        List<CartProduct> cartProducts = new ArrayList<>();
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cartId);
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(quantity);
        cartProducts.add(cartProduct);

        doReturn(Mono.just(cart)).when(cartRepository).findCartByUserId(userId);
        doReturn(Flux.fromIterable(cartProducts)).when(cartProductRepository).findAllById(List.of(cart.getId()));
        doReturn(Mono.just(product)).when(productService).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());

        Cart resultCart = cartService.getCartByUserId(userId).block();

        assertNotNull(resultCart, "Cart should not be null");

        assertEquals(userId, resultCart.getUserId(), "Wrong cart was retrieved");
        verify(cartRepository, times(1)).findCartByUserId(userId);
        verify(cartProductRepository, times(1)).findAllById(List.of(cart.getId()));
        verify(productService, times(1)).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());
    }

    @Test
    void testSetProductToCartProduct() {
        Integer cartId = 1;
        Integer productId = 2;
        Integer quantity = 3;

        Product product = new Product();
        product.setId(productId);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cartId);
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(quantity);

        doReturn(Mono.just(product)).when(productService).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());

        CartProduct result = cartService.setProductToCartProduct(cartProduct).block();

        assertNotNull(result, "CartProduct should not be null");

        assertEquals(cartId, result.getCartId(), "Wrong cartProduct was retrieved");
        assertEquals(productId, result.getProduct().getId(), "Wrong product was retrieved");
        verify(productService, times(1)).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());
    }

    @Test
    void testDecreaseProductCount() {
        Integer userId = 1;
        Integer cartId = 2;
        Integer productId = 3;
        Integer quantity = 4;

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);

        Product product = new Product();
        product.setId(productId);

        List<CartProduct> cartProducts = new ArrayList<>();
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cartId);
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(quantity);
        cartProducts.add(cartProduct);

        doReturn(Mono.just(cart)).when(cartRepository).findCartByUserId(userId);
        doReturn(Flux.fromIterable(cartProducts)).when(cartProductRepository).findAllById(List.of(cart.getId()));
        doReturn(Mono.just(product)).when(productService).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());

        doAnswer(invocation -> {
            cartProduct.setQuantity(cartProduct.getQuantity() - 1);
            return Mono.just(cartProduct);
        }).when(cartProductRepository).saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity() - 1));

        cartService.decreaseProductCount(userId, product).block();

        assertEquals(3, cart.getCartProducts().get(0).getQuantity(), "Quantity was not decreased properly");

        verify(cartRepository, times(2)).findCartByUserId(userId);
        verify(cartProductRepository, times(2)).findAllById(List.of(cart.getId()));
        verify(productService, times(2)).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());
        verify(cartProductRepository, times(1)).saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity()));
    }

    @Test
    void testIncreaseProductCount() {
        Integer userId = 1;
        Integer cartId = 2;
        Integer productId = 3;
        Integer quantity = 4;

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);

        Product product = new Product();
        product.setId(productId);

        List<CartProduct> cartProducts = new ArrayList<>();
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cartId);
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(quantity);
        cartProducts.add(cartProduct);

        doReturn(Mono.just(cart)).when(cartRepository).findCartByUserId(userId);
        doReturn(Flux.fromIterable(cartProducts)).when(cartProductRepository).findAllById(List.of(cart.getId()));
        doReturn(Mono.just(product)).when(productService).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());

        doAnswer(invocation -> {
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
            return Mono.just(cartProduct);
        }).when(cartProductRepository).saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity() + 1));

        cartService.increaseProductCount(userId, product).block();

        assertEquals(5, cart.getCartProducts().get(0).getQuantity(), "Quantity was not increased properly");

        verify(cartRepository, times(2)).findCartByUserId(userId);
        verify(cartProductRepository, times(2)).findAllById(List.of(cart.getId()));
        verify(productService, times(2)).getProductCacheable(cartProduct.getProductId().toString(), cartProduct.getProductId());
        verify(cartProductRepository, times(1)).saveByCartIdAndProductId(cartProduct.getCartId(), cartProduct.getProductId(), (cartProduct.getQuantity()));
    }

    @Test
    void testDeleteProduct() {
        Integer userId = 1;
        Integer cartId = 2;
        Integer p1_id = 3;
        Integer p1_quantity = 5;
        Integer p2_id = 4;
        Integer p2_quantity = 10;

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);

        Product p1 = new Product();
        p1.setId(p1_id);
        Product p2 = new Product();
        p2.setId(p2_id);

        List<CartProduct> cartProducts = new ArrayList<>();
        CartProduct cp1 = new CartProduct();
        cp1.setCartId(cartId);
        cp1.setProductId(p1_id);
        cp1.setQuantity(p1_quantity);
        cartProducts.add(cp1);

        CartProduct cp2 = new CartProduct();
        cp2.setCartId(cartId);
        cp2.setProductId(p2_id);
        cp2.setQuantity(p2_quantity);
        cartProducts.add(cp2);

        doReturn(Mono.just(cart)).when(cartRepository).findCartByUserId(userId);
        doReturn(Flux.fromIterable(cartProducts)).when(cartProductRepository).findAllById(List.of(cart.getId()));
        doReturn(Mono.just(p1)).when(productService).getProductCacheable(cp1.getProductId().toString(), cp1.getProductId());
        doReturn(Mono.just(p2)).when(productService).getProductCacheable(cp2.getProductId().toString(), cp2.getProductId());

        doAnswer(invocation -> {
            cartProducts.remove(cp1);
            return Mono.just(cp1);
        }).when(cartProductRepository).deleteByCartIdAndProductId(cp1.getCartId(), cp1.getProductId());

        cartService.deleteProduct(userId, p1).block();

        assertEquals(1, cart.getCartProducts().size(), "Product was not deleted from the Cart");
        assertEquals(p2_id, cart.getCartProducts().get(0).getProductId(), "Wrong Product was not deleted from the Cart");

        verify(cartRepository, times(2)).findCartByUserId(userId);
        verify(cartProductRepository, times(2)).findAllById(List.of(cart.getId()));
        verify(cartProductRepository, times(1)).deleteByCartIdAndProductId(cp1.getCartId(), cp1.getProductId());
    }

    @Test
    void testDelete() {
        Integer userId = 1;
        Cart cart = new Cart();
        cart.setUserId(userId);

        doReturn(Mono.empty()).when(cartRepository).delete(cart);

        cartService.delete(cart).block();

        verify(cartRepository, times(1)).delete(cart);
    }
}
