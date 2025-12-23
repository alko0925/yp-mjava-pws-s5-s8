package ru.yp.sprint5pw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Objects;

@Entity
@Table(name = "cart_products")
public class CartProduct {

    @EmbeddedId
    @JsonIgnore
    private CartProductPK pk;

    private Integer quantity;

    public CartProduct() {
    }

    public CartProduct(Cart cart, Product product, Integer quantity) {
        pk = new CartProductPK();
        pk.setCart(cart);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

    @Transient
    public Long getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public CartProductPK getPk() {
        return pk;
    }

    public void setPk(CartProductPK pk) {
        this.pk = pk;
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
        return Objects.equals(pk, that.pk) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, quantity);
    }
}
