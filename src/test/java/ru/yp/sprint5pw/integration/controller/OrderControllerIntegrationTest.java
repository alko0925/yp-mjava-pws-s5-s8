package ru.yp.sprint5pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class OrderControllerIntegrationTest extends MyMarketApplicationWebTest {
    @Test
    void getOrders_returnsViewOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(1)));
    }

    @Test
    void getOrder_returnsRedirectToCartOrder() throws Exception {
        Integer orderId = orderRepository.findAll(Sort.by("id").descending()).getFirst().getId();
        mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("newOrder"));
    }
}
