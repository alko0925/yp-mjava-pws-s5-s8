package ru.yp.sprint6pw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table(schema = "mart_app", name = "carts")
public class Cart {

    @Id
    private Integer id;

    private Integer userId;

    public Cart() {
    }

    public Cart(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    @Transient
    private List<CartProduct> cartProducts = new ArrayList<>();

    @Transient
    public CartProduct getCartProductByProduct(Product product) {
        return  getCartProducts().stream()
                .filter(cp -> cp.getProductId().equals(product.getId()))
                .findFirst().orElse(null);
    }

    @Transient
    public Long getTotalCartPrice() {
        Long sum = 0L;
        List<CartProduct> cartProducts = getCartProducts();
        for (CartProduct cp : cartProducts) {
            sum += cp.getTotalPrice();
        }

        return sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
