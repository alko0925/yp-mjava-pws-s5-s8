package ru.yp.sprint5pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import ru.yp.sprint5pw.model.Order;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RootControllerIntegrationTest extends MyMarketApplicationWebTest {

    @Test
    void getItems_returnsRedirectionToItems() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items?search=&sort=NO&pageNumber=1&pageSize=5"));
    }

    @Test
    void submitOrder_returnsRedirectionToOrders() throws Exception {
        int orderId = 1;
        List<Order> orders = orderRepository.findAll(Sort.by("id").descending());
        if (!orders.isEmpty()) orderId = orders.getFirst().getId() + 1;

        mockMvc.perform(post("/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/" + orderId + "?newOrder=true"));
    }
}
