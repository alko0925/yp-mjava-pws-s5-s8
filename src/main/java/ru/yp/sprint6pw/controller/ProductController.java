package ru.yp.sprint6pw.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yp.sprint6pw.model.Product;
import ru.yp.sprint6pw.service.FilesService;
import ru.yp.sprint6pw.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FilesService filesService;

    public ProductController(ProductService productService, FilesService filesService) {
        this.productService = productService;
        this.filesService = filesService;
    }

//    @GetMapping("/add")
//    public String displayAddForm(Model model) {
//        model.addAttribute("product", new Product());
//        return "add-product";
//    }

//    @PostMapping("/add")
//    public String addPost(@ModelAttribute("product") Product product,
//                          @RequestParam("image") MultipartFile image,
//                          Model model) {
//
//        product = productService.create(product);
//        product.setImgPath("product/image/" + product.getId() + "_" + image.getOriginalFilename());
//        productService.update(product);
//        filesService.upload(product.getId() + "_" + image.getOriginalFilename(), image);
//
//        return "redirect:/items/" +
//                product.getId();
//    }

//    @GetMapping("/image/{image_id}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable(name = "image_id") String imageId) {
//        Resource file = filesService.download(imageId);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(file);
//    }
}
