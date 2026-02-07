package ru.yp.sprint7pw.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.client.PaymentServiceClient;
import ru.yp.sprint7pw.client.domain.BalanceResponse;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

public class CartControllerIntegrationTest extends MartApplicationWebTest {

    @MockitoBean
    private PaymentServiceClient paymentServiceClient;

    @BeforeEach
    void resetMocks() {
        reset(paymentServiceClient);
    }

    @Test
    void getItems_returnsViewCart() throws Exception {

        doReturn(Mono.just(new BalanceResponse().balance(20000D))).when(paymentServiceClient).getBalance(Mockito.any());

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
