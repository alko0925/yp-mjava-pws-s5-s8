package ru.yp.sprint7pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.ItemDto;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.service.CartService;
import ru.yp.sprint7pw.service.ProductService;

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
    public Mono<Rendering> getItems() {

        return cartService.getCartByUserId(1)
                .map(cart -> {
                    List<ItemDto> items = new ArrayList<>();
                    Long total = 0L;
                    if (cart.getId() != null) {
                        items = cart.getCartProducts().stream().map(cp -> {
                            ItemDto item = new ItemDto();
                            item.setId(cp.getProduct().getId());
                            item.setTitle(cp.getProduct().getTitle());
                            item.setDescription(cp.getProduct().getDescription());
                            item.setImgPath(cp.getProduct().getImgPath());
                            item.setPrice(cp.getProduct().getPrice());
                            item.setCount(cp.getQuantity());
                            return item;
                        }).toList();
                        total = cart.getTotalCartPrice();
                    }

                    return Rendering.view("cart")
                            .modelAttribute("items", items)
                            .modelAttribute("total", total)
                            .build();
                });
    }

    @PostMapping(value = "/items")
    public Mono<String> applyActionToItem(ServerWebExchange swe) {

        Mono<MultiValueMap<String, String>> fd = swe.getFormData();
        Mono<Product> product = fd.flatMap(map -> productService.getProduct(Integer.parseInt(map.getFirst("id"))));

        return Mono.zip(fd, product)
                .flatMap(tuple -> {
                            MultiValueMap<String, String> map = tuple.getT1();
                            Product p = tuple.getT2();

                            return switch (ActionType.valueOf(map.getFirst("action"))) {
                                case MINUS -> cartService.decreaseProductCount(1, p);
                                case PLUS -> cartService.increaseProductCount(1, p);
                                case DELETE -> cartService.deleteProduct(1, p);
                            };
                        }
                ).thenReturn("redirect:/cart/items");
    }
}
