package ru.yp.sprint7pw.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.model.Product;
import ru.yp.sprint7pw.service.FilesService;
import ru.yp.sprint7pw.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FilesService filesService;

    public ProductController(ProductService productService, FilesService filesService) {
        this.productService = productService;
        this.filesService = filesService;
    }

    @GetMapping("/add")
    public Mono<Rendering> displayAddForm() {
        return Mono.just(
                Rendering.view("add-product")
                        .modelAttribute("product", new Product())
                        .build()
        );
    }

    @PostMapping("/add")
    public Mono<String> addProduct(@ModelAttribute("product") Product product,
                                   @RequestPart("image") FilePart image) {

        return productService.clearCaches()
                .then(productService.create(product))
                .flatMap(p -> {
                    p.setImgPath("product/image/" + p.getId() + "_" + image.filename());
                    return productService.update(p);
                })
                .flatMap(p -> filesService.upload(p, image))
                .map(p -> "redirect:/items/" + p.getId());
    }

    @GetMapping("/image/{image_id}")
    public Mono<ResponseEntity<Resource>> downloadImage(@PathVariable(name = "image_id") String imageId) {
        return filesService.download(imageId)
                .map(file -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(file));
    }
}
