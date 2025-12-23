package ru.yp.sprint6pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class CartControllerIntegrationTest extends MyMarketApplicationWebTest {

    @Test
    void getItems_returnsViewCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", hasSize(2)))
                .andExpect(model().attributeExists("total"));
    }

    @Test
    void applyPlusActionToItems_returnsRedirectToCartItems() throws Exception {
        Integer productId = productRepository.findAll(Sort.by("id").descending()).getFirst().getId();
        mockMvc.perform(post("/cart/items?id=" + productId + "&action=PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/items"));
    }
}
