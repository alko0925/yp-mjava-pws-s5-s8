package ru.yp.sprint5pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yp.sprint5pw.controller.dto.ItemDto;
import ru.yp.sprint5pw.model.Cart;

import ru.yp.sprint5pw.service.CartService;
import ru.yp.sprint5pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;


    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping(value = "/items")
    public String getItems(Model model) {

        Cart cart = cartService.getCartByUserId(1);
        List<ItemDto> items = new ArrayList<>();
        Long total = 0L;

        if (cart != null) {
            for (var cp : cart.getCartProducts()) {
                ItemDto item = new ItemDto();
                item.setId(cp.getProduct().getId());
                item.setTitle(cp.getProduct().getTitle());
                item.setDescription(cp.getProduct().getDescription());
                item.setImgPath(cp.getProduct().getImgPath());
                item.setPrice(cp.getProduct().getPrice());
                item.setCount(cp.getQuantity());
                items.add(item);
            }
            total = cart.getTotalCartPrice();
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "cart";
    }
}
