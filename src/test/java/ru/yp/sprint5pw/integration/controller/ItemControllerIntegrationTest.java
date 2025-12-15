package ru.yp.sprint5pw.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerIntegrationTest extends MyMarketApplicationWebTest {

    @Test
    void getItems_returnsViewItems() throws Exception {
        mockMvc.perform(get("/items?search=&sort=&pageNumber=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", hasSize(1)))
                .andExpect(model().attributeExists("search"))
                .andExpect(model().attributeExists("sort"))
                .andExpect(model().attributeExists("paging"));
    }

    @Test
    void applyPlusActionToItems_returnsRedirectToCartItems() throws Exception {

        Integer productId = productRepository.findAll(Sort.by("id").descending()).getFirst().getId();
        mockMvc.perform(post("/items?id=" + productId + "&action=PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items?search=&sort=NO&pageNumber=1&pageSize=5"));
    }

    @Test
    void getItem_returnsViewItem() throws Exception {
        Integer productId = productRepository.findAll(Sort.by("id").descending()).getFirst().getId();
        mockMvc.perform(get("/items/" + productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    void applyPlusActionToItem_returnsRedirectToItem() throws Exception {

        Integer productId = productRepository.findAll(Sort.by("id").descending()).getFirst().getId();
        mockMvc.perform(post("/items/" + productId + "?action=PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/" + productId));
    }
}