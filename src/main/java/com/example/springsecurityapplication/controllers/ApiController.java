package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final ProductService productService;

    public ApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/infoProduct/{id}")
    public Product apiInfoProduct(@PathVariable int id) {
        return productService.getProductId(id);
    }

    @GetMapping("/all")
    public List<Product> apiListProduct() {
        return productService.getAllProduct();
    }
}
