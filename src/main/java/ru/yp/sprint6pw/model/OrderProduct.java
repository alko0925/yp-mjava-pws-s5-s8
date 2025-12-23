package ru.yp.sprint6pw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Objects;

@Entity
@Table(name = "order_products")
public class OrderProduct {
    @EmbeddedId
    @JsonIgnore
    private OrderProductPK pk;

    private Integer quantity;

    public OrderProduct() {
        super();
    }

    public OrderProduct(Order order, Product product, Integer quantity) {
        pk = new OrderProductPK();
        pk.setOrder(order);
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

    public OrderProductPK getPk() {
        return pk;
    }

    public void setPk(OrderProductPK pk) {
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
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(pk, that.pk) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, quantity);
    }
}
