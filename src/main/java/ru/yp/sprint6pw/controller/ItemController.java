package ru.yp.sprint6pw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yp.sprint6pw.controller.dto.ItemDto;
import ru.yp.sprint6pw.controller.dto.PageParams;
import ru.yp.sprint6pw.model.Cart;
import ru.yp.sprint6pw.model.CartProduct;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.service.CartService;
import ru.yp.sprint6pw.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ProductService productService;
    private final CartService cartService;


    public ItemController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

//    @GetMapping
//    public String getItems(@RequestParam(defaultValue = "") String search,
//                           @RequestParam(defaultValue = "NO") String sort,
//                           @RequestParam(defaultValue = "1") Integer pageNumber,
//                           @RequestParam(defaultValue = "5") Integer pageSize,
//                           @Value("${page.layout.rowsize}") Integer pageRowSize,
//                           Model model) {
//
//        PageParams pageParams = new PageParams(pageNumber, pageSize, false, false);
//
//        List<Product> products = productService.getProducts(search, sort, pageParams);
//        List<List<ItemDto>> items = new ArrayList<>();
//        List<ItemDto> itemsInner = new ArrayList<>();
//
//        Cart cart = cartService.getCartByUserId(1);
//
//        int i = 1;
//        for (var p : products) {
//            ItemDto item = new ItemDto();
//            item.setId(p.getId());
//            item.setTitle(p.getTitle());
//            item.setDescription(p.getDescription());
//            item.setImgPath(p.getImgPath());
//            item.setPrice(p.getPrice());
//            if (cart != null) {
//                CartProduct cartProduct = cart.getCartProducts().stream()
//                        .filter(cp -> cp.getProduct().getId().equals(p.getId()))
//                        .findFirst().orElse(null);
//                if (cartProduct != null) item.setCount(cartProduct.getQuantity());
//                else item.setCount(0);
//            } else item.setCount(0);
//            itemsInner.add(item);
//
//            if (i == pageRowSize) {
//                items.add(itemsInner);
//                itemsInner = new ArrayList<>();
//                i = 1;
//            } else i++;
//        }
//
//        if (!itemsInner.isEmpty()) {
//            while (itemsInner.size() < pageRowSize) {
//                ItemDto item = new ItemDto();
//                item.setId(-1);
//                itemsInner.add(item);
//            }
//            items.add(itemsInner);
//        }
//
//        model.addAttribute("items", items);
//        model.addAttribute("search", search);
//        model.addAttribute("sort", sort);
//        model.addAttribute("paging", pageParams);
//
//        return "items";
//    }

//    @PostMapping
//    public String applyActionToItems(@RequestParam("id") Integer item_id,
//                                     @RequestParam(defaultValue = "") String search,
//                                     @RequestParam(defaultValue = "NO") String sort,
//                                     @RequestParam(defaultValue = "1") Integer pageNumber,
//                                     @RequestParam(defaultValue = "5") Integer pageSize,
//                                     @RequestParam("action") String action) {
//
//        Product p = productService.getProduct(item_id);
//        if (ActionType.MINUS.toString().equals(action)) cartService.decreaseProductCount(1, p);
//        if (ActionType.PLUS.toString().equals(action)) cartService.increaseProductCount(1, p);
//
//        return "redirect:/items?search=" +
//                search +
//                "&sort=" +
//                sort +
//                "&pageNumber=" +
//                pageNumber +
//                "&pageSize=" +
//                pageSize;
//    }

//    @GetMapping(value = "/{item_id}")
//    public String getItem(@PathVariable("item_id") Integer item_id,
//                          Model model) {
//
//        Cart cart = cartService.getCartByUserId(1);
//        Product p = productService.getProduct(item_id);
//
//        ItemDto item = new ItemDto();
//        item.setId(p.getId());
//        item.setTitle(p.getTitle());
//        item.setDescription(p.getDescription());
//        item.setImgPath(p.getImgPath());
//        item.setPrice(p.getPrice());
//        if (cart != null) {
//            CartProduct cartProduct = cart.getCartProducts().stream()
//                    .filter(cp -> cp.getProduct().getId().equals(p.getId()))
//                    .findFirst().orElse(null);
//            if (cartProduct != null) item.setCount(cartProduct.getQuantity());
//            else item.setCount(0);
//        } else item.setCount(0);
//
//        model.addAttribute("item", item);
//
//        return "item";
//    }

//    @PostMapping(value = "/{item_id}")
//    public String applyActionToItem(@PathVariable("item_id") Integer item_id,
//                                    @RequestParam("action") String action) {
//
//        Product p = productService.getProduct(item_id);
//        if (ActionType.MINUS.toString().equals(action)) cartService.decreaseProductCount(1, p);
//        if (ActionType.PLUS.toString().equals(action)) cartService.increaseProductCount(1, p);
//
//        return "redirect:/items/" +
//                item_id;
//    }
}
