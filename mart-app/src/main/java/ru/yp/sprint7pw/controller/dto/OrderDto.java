package ru.yp.sprint7pw.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    private Integer id;
    private List<ItemDto> items = new ArrayList<>();
    private Long totalSum = 0L;

    public OrderDto(){
    }

    public OrderDto(Integer id, List<ItemDto> items, Long totalSum) {
        this.id = id;
        this.items = items;
        this.totalSum = totalSum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public Long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum = totalSum;
    }
}
