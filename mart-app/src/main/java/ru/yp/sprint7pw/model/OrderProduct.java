package ru.yp.sprint7pw.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(schema = "mart_app", name = "order_products")
public class OrderProduct {

    @Id
    private Integer orderId;

    @Transient
    private Order order;

    private Integer productId;

    @Transient
    private Product product;

    private Integer quantity;

    public OrderProduct() {
    }

    public OrderProduct(Integer orderId, Integer productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Transient
    public Long getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(order, that.order) && Objects.equals(productId, that.productId) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, order, productId, product, quantity);
    }
}
