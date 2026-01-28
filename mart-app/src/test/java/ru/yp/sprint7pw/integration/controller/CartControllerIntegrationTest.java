package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

public class CartControllerIntegrationTest extends MartApplicationWebTest {

    @Test
    void getItems_returnsViewCart() throws Exception {
        webTestClient.get()
                .uri("/cart/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("<h5 class=\"card-title\">Prod1</h5>");
                    assert html.contains("<span class=\"badge text-bg-success justify-content-end\">1000 руб.</span>");
                    assert html.contains("<span>2</span>");
                    assert html.contains("<h5 class=\"card-title\">Prod2</h5>");
                    assert html.contains("<span class=\"badge text-bg-success justify-content-end\">2000 руб.</span>");
                    assert html.contains("<span>4</span>");
                    assert html.contains("<h2>Итого: 10000 руб.</h2>");

                });
    }

    @Test
    void applyPlusActionToItems_returnsRedirectToCartItems() throws Exception {
        Integer productId = productRepository.findProductsByCriterias("%prod1%", 0, 5)
                .collectList()
                .block()
                .getFirst()
                .getId();

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("id", productId.toString());
        formData.add("action", "PLUS");

        webTestClient.post()
                .uri("/cart/items")
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/cart/items");
    }
}
