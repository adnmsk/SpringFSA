package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class MainController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public MainController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // Данный метод предназначен для отображении товаров без прохождения аутентификации и авторизации
    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("search_product", productService.getAllProduct());
        return "product/product";
    }

    //Метод для вывода на страницу поиска и сортировки
    @GetMapping("findProduct")
    public String findAllProduct(Model model){
        model.addAttribute("search_product", productService.getAllProduct());
        return "product/findProduct";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model){
        search = search.toLowerCase();

        System.out.println(search);
        System.out.println(ot);
        System.out.println(Do);
        System.out.println(price);
        System.out.println(category);

        double from;
        double to;

        if (ot.isEmpty()) {
            from = 0D;
        } else {
            from = Double.parseDouble(ot);
        }

        if (Do.isEmpty()) {
            to = Double.MAX_VALUE;
        } else {
            to = Double.parseDouble(Do);
        }

        List<Product> searchList = new ArrayList<>();
        if (category.isEmpty()) {
            if (price.equals("sorted_by_ascending_price")) {
                searchList = productRepository.searchWithoutCategoryAcsPrice(search, from, to);
            }
            if (price.equals("sorted_by_descending_price")) {
                searchList = productRepository.searchWithoutCategoryDescPrice(search, from, to);
            }
            if (price.isEmpty()) {
                searchList = productRepository.searchWithoutCategory(search, from, to);
            }
        } else {
            if (price.equals("sorted_by_ascending_price")) {
                searchList = productRepository.searchWithCategoryAcsPrice(search, from, to, Integer.parseInt(category));
            }
            if (price.equals("sorted_by_descending_price")) {
                searchList = productRepository.searchWithCategoryDescPrice(search, from, to, Integer.parseInt(category));
            }
            if (price.isEmpty()) {
                searchList = productRepository.searchWithCategory(search, from, to, Integer.parseInt(category));
            }
        }

//        // Если диапазон цен от и до не пустой
//        if(!ot.isEmpty() && !Do.isEmpty()) {
//            // Если сортировка по цене выбрана
//            if (!price.isEmpty()) {
//                // Если в качестве сортировки выбрана сортировкам по возрастанию
//                if (price.equals("sorted_by_ascending_price")) {
//                    // Если категория товара не пустая
//                    if (!category.isEmpty()) {
//                        // Если категория равная мебели
//                        if (category.equals("furniture")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
//                            // Если категория равная бытовой техники
//                        } else if (category.equals("appliances")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
//                            // Если категория равная одежде
//                        } else if (category.equals("clothes")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
//                        }
//                        // Если категория не выбрана
//                    } else {
//                        model.addAttribute("search_product", productRepository.findByTitleOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
//                    }
//
//                    // Если в качестве сортировки выбрана сортировкам по убыванию
//                } else if (price.equals("sorted_by_descending_price")) {
//
//                    // Если категория не пустая
//                    if (!category.isEmpty()) {
//                        // Если категория равная мебели
//                        if (category.equals("furniture")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
//                            // Если категория равная бытовой техники
//                        } else if (category.equals("appliances")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
//                            // Если категория равная одежде
//                        } else if (category.equals("clothes")) {
//                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
//                        }
//                        // Если категория не выбрана
//                    }
//                    else {
//                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
//                    }
//                }
//            }
//            else {
//                model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
//            }
//        } else {
//            model.addAttribute("search_product",productRepository.findByTitleContainingIgnoreCaseOrderByTitleAsc(search));
//        }
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute("search_product", searchList);
        return "product/findProduct";
    }


}
