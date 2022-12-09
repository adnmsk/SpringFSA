package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//Метод дает лист всех заказов для управления админом
    //доступен по /orders/orderManagement
    @GetMapping("/orderManagement")
    public String getAllOrders(Model model) {
        model.addAttribute("orders_list", orderService.getAllOrders());
        return "order/orderManagement";
    }

    @GetMapping("/findByNumber")
    public String getAllOrders(Model model, @RequestParam("lastNumber") String lastNumber) {
        List<Order> orderList = orderService.getByLastNumbers(lastNumber);
        model.addAttribute("orders_list", orderList);
        return "order/orderManagement";
    }

    @GetMapping("/editOrder")
    public String editOrder() {
        return "";
    }
}
