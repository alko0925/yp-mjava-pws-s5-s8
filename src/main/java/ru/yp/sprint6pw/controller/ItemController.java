package ru.yp.sprint6pw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yp.sprint6pw.controller.dto.ItemDto;
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

    @GetMapping
    public Mono<Rendering> getItems(@RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "NO") String sort,
                                    @RequestParam(defaultValue = "1") Integer pageNumber,
                                    @RequestParam(defaultValue = "5") Integer pageSize,
                                    @Value("${page.layout.rowsize}") Integer pageRowSize,
                                    Model model) {

        return productService.getProducts(search, sort, pageNumber, pageSize)
                .zipWith(cartService.getCartByUserId(1))
                .map(tuple -> {
                    List<List<ItemDto>> items = new ArrayList<>();
                    List<ItemDto> itemsInner = new ArrayList<>();
                    int i = 1;
                    for (var p : tuple.getT1().getProducts()) {
                        ItemDto item = new ItemDto();
                        item.setId(p.getId());
                        item.setTitle(p.getTitle());
                        item.setDescription(p.getDescription());
                        item.setImgPath(p.getImgPath());
                        item.setPrice(p.getPrice());

                        Cart cart = tuple.getT2();
                        if (cart.getId() != null) {
                            CartProduct cartProduct = cart.getCartProducts().stream()
                                    .filter(cp -> cp.getProductId().equals(p.getId()))
                                    .findFirst().orElse(null);
                            if (cartProduct != null) item.setCount(cartProduct.getQuantity());
                            else item.setCount(0);
                        } else item.setCount(0);
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

                    return Rendering.view("items")
                            .modelAttribute("items", items)
                            .modelAttribute("search", search)
                            .modelAttribute("sort", sort)
                            .modelAttribute("paging", tuple.getT1().getPageParams())
                            .build();
                });
    }

    @PostMapping
    public Mono<String> applyActionToItems(ServerWebExchange swe) {

        Mono<MultiValueMap<String, String>> fd = swe.getFormData();
        Mono<Product> product = fd.flatMap(map -> productService.getProduct(Integer.parseInt(map.getFirst("id"))));

        return Mono.zip(fd, product)
                .flatMap(tuple -> {
                    MultiValueMap<String, String> map = tuple.getT1();
                    Product p = tuple.getT2();
                    if (ActionType.MINUS.toString().equals(map.getFirst("action")))
                        return cartService.decreaseProductCount(1, p);
                    else
                        return cartService.increaseProductCount(1, p);
                }).then(swe.getFormData()).map(map -> {
                    return "redirect:/items?search=" +
                            map.getFirst("search") +
                            "&sort=" +
                            map.getFirst("sort") +
                            "&pageNumber=" +
                            map.getFirst("pageNumber") +
                            "&pageSize=" +
                            map.getFirst("pageSize");
                });
    }

    @GetMapping(value = "/{item_id}")
    public Mono<Rendering> getItem(@PathVariable("item_id") Integer item_id,
                                   Model model) {

        return productService.getProduct(item_id)
                .zipWith(cartService.getCartByUserId(1))
                .map(tuple -> {
                    Product p = tuple.getT1();
                    Cart cart = tuple.getT2();

                    ItemDto item = new ItemDto();
                    item.setId(p.getId());
                    item.setTitle(p.getTitle());
                    item.setDescription(p.getDescription());
                    item.setImgPath(p.getImgPath());
                    item.setPrice(p.getPrice());
                    if (cart.getId() != null) {
                        CartProduct cartProduct = cart.getCartProducts().stream()
                                .filter(cp -> cp.getProductId().equals(p.getId()))
                                .findFirst().orElse(null);
                        if (cartProduct != null) item.setCount(cartProduct.getQuantity());
                        else item.setCount(0);
                    } else item.setCount(0);

                    return Rendering.view("item")
                            .modelAttribute("item", item)
                            .build();
                });
    }

    @PostMapping(value = "/{item_id}")
    public Mono<String> applyActionToItem(@PathVariable("item_id") Integer item_id,
                                          ServerWebExchange swe) {

        return productService.getProduct(item_id)
                .zipWith(swe.getFormData())
                .flatMap(
                        tuple -> {
                            Product p = tuple.getT1();
                            MultiValueMap<String, String> map = tuple.getT2();
                            if (ActionType.MINUS.toString().equals(map.getFirst("action")))
                                return cartService.decreaseProductCount(1, p);
                            else
                                return cartService.increaseProductCount(1, p);
                        }
                ).thenReturn("redirect:/items/" +
                        item_id);
    }
}
