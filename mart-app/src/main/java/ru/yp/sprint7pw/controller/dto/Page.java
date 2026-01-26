package ru.yp.sprint7pw.controller.dto;

import ru.yp.sprint7pw.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private List<Product> products = new ArrayList<>();
    private PageParams pageParams;

    public Page() {
    }

    public Page(List<Product> products, PageParams pageParams) {
        this.products = products;
        this.pageParams = pageParams;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public PageParams getPageParams() {
        return pageParams;
    }

    public void setPageParams(PageParams pageParams) {
        this.pageParams = pageParams;
    }
}
