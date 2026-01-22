package ru.yp.sprint6pw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "cart_products")
public class CartProduct {

    @Id
    private Integer cartId;

    @Transient
    private Cart cart;

    private Integer productId;

    @Transient
    private Product product;

    private Integer quantity;

    public CartProduct() {
    }

    public CartProduct(Integer cartId, Integer productId, Integer quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Transient
    public Long getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return Objects.equals(cartId, that.cartId) && Objects.equals(cart, that.cart) && Objects.equals(productId, that.productId) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, cart, productId, product, quantity);
    }
}
