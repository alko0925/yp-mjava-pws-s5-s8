package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

class ItemControllerIntegrationTest extends MyMarketApplicationWebTest {

    @Test
    void getItems_returnsViewItems() throws Exception {
        webTestClient.get()
                .uri("/items?search=&sort=&pageNumber=1&pageSize=5")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("<h5 class=\"card-title\">Prod1</h5>");
                    assert html.contains("<span>2</span>");
                    assert html.contains("<h5 class=\"card-title\">Prod2</h5>");
                    assert html.contains("<span>4</span>");
                    assert html.contains("<h5 class=\"card-title\">Prod3</h5>");
                    assert html.contains("<span>0</span>");
                });
    }

    @Test
    void applyPlusActionToItems_returnsRedirectToViewItems() throws Exception {

        Integer productId = productRepository.findProductsByCriterias("%prod1%", 0, 5)
                .collectList()
                .block()
                .getFirst()
                .getId();

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("id", productId.toString());
        formData.add("search", "");
        formData.add("sort", "NO");
        formData.add("pageNumber", "1");
        formData.add("pageSize", "5");
        formData.add("action", "PLUS");

        webTestClient.post()
                .uri("/items")
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items?search=&sort=NO&pageNumber=1&pageSize=5");
    }

    @Test
    void getItem_returnsViewItem() throws Exception {
        Integer productId = productRepository.findProductsByCriterias("%prod1%", 0, 5)
                .collectList()
                .block()
                .getFirst()
                .getId();

        webTestClient.get()
                .uri("/items/" + productId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("<h5 class=\"card-title\">Prod1</h5>");
                    assert html.contains("<span>2</span>");
                });
    }

    @Test
    void applyPlusActionToItem_returnsRedirectToViewItem() throws Exception {
        Integer productId = productRepository.findProductsByCriterias("%prod1%", 0, 5)
                .collectList()
                .block()
                .getFirst()
                .getId();

        webTestClient.post()
                .uri("/items/" + productId + "?action=PLUS")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items/" + productId);
    }
}