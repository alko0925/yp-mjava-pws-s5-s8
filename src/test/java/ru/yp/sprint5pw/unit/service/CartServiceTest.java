package ru.yp.sprint5pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yp.sprint5pw.MyMarketApplicationTest;
import ru.yp.sprint5pw.model.Cart;
import ru.yp.sprint5pw.model.CartProduct;
import ru.yp.sprint5pw.model.Product;
import ru.yp.sprint5pw.repository.CartProductRepository;
import ru.yp.sprint5pw.repository.CartRepository;
import ru.yp.sprint5pw.service.CartService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartServiceTest extends MyMarketApplicationTest {

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private CartProductRepository cartProductRepository;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void resetMocks() {
        reset(cartRepository, cartProductRepository);
    }

    @Test
    void testGetCartByUserId() {
        Integer userId = 1;
        Cart cart = new Cart();
        cart.setUserId(userId);

        doReturn(cart).when(cartRepository).findCartByUserId(userId);
        Cart resultCart = cartService.getCartByUserId(userId);

        assertNotNull(resultCart, "Cart should not be null");

        assertEquals(userId, resultCart.getUserId(), "Wrong cart was retrieved");
        verify(cartRepository, times(1)).findCartByUserId(userId);
    }

    @Test
    void testDecreaseProductCount() {
        Integer userId = 1;
        Cart cart = new Cart();
        cart.setUserId(userId);

        Product p1 = new Product(1, "Prod1", "", "", 1000L);
        Product p2 = new Product(2, "Prod2", "", "", 2000L);

        CartProduct cp1 = new CartProduct(cart, p1, 3);
        CartProduct cp2 = new CartProduct(cart, p2, 5);

        cart.getCartProducts().add(cp1);
        cart.getCartProducts().add(cp2);

        doReturn(cart).when(cartRepository).findCartByUserId(userId);
        cartService.decreaseProductCount(userId, p2);

        assertEquals(4, cart.getCartProducts().get(1).getQuantity(), "Quantity was not decreased properly");

        verify(cartRepository, times(1)).findCartByUserId(userId);
        verify(cartRepository, times(1)).save(cart);
    }
}
