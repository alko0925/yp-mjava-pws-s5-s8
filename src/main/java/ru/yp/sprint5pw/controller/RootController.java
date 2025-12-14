package ru.yp.sprint5pw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String getItems(@RequestParam(defaultValue = "") String search,
                           @RequestParam(defaultValue = "NO") String sort,
                           @RequestParam(defaultValue = "1") Integer pageNumber,
                           @RequestParam(defaultValue = "5") Integer pageSize) {

        return "redirect:/items?search=" +
                search +
                "&sort=" +
                sort +
                "&pageNumber=" +
                pageNumber +
                "&pageSize=" +
                pageSize;
    }
}
