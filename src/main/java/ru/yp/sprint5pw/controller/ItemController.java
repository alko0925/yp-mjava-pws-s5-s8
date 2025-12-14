package ru.yp.sprint5pw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yp.sprint5pw.controller.dto.ItemDto;
import ru.yp.sprint5pw.controller.dto.PageParams;
import ru.yp.sprint5pw.model.Product;
import ru.yp.sprint5pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getItems(@RequestParam(defaultValue = "") String search,
                           @RequestParam(defaultValue = "NO") String sort,
                           @RequestParam(defaultValue = "1") Integer pageNumber,
                           @RequestParam(defaultValue = "5") Integer pageSize,
                           @Value("${page.layout.rowsize}") Integer pageRowSize,
                           Model model) {

        PageParams pageParams = new PageParams(pageNumber, pageSize, false, false);

        Iterable<Product> products = productService.getProducts(search, sort, pageParams);
        List<List<ItemDto>> items = new ArrayList<>();
        List<ItemDto> itemsInner = new ArrayList<>();

        int i = 1;
        for (var p : products) {
            ItemDto item = new ItemDto();
            item.setId(p.getId());
            item.setTitle(p.getTitle());
            item.setDescription(p.getDescription());
            item.setImgPath(p.getImgPath());
            item.setPrice(p.getPrice());
            item.setCount(0);
            itemsInner.add(item);

            if (i == pageRowSize) {
                items.add(itemsInner);
                itemsInner = new ArrayList<>();
                i = 1;
            } else i++;
        }

        if (!itemsInner.isEmpty()) {
            while (itemsInner.size() < pageRowSize) {
                ItemDto item = new ItemDto();
                item.setId(-1);
                itemsInner.add(item);
            }
            items.add(itemsInner);
        }

        model.addAttribute("items", items);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("paging", pageParams);

        return "items";
    }
}
