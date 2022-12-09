package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//Метод дает лист всех заказов для управления админом
    //доступен по /order/orderManagement
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

    @GetMapping("editOrder/{id}")
    public String getOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.findById(id));
        return "order/editOrder";
    }

    @PostMapping("/editOrder/{id}")
    public String editOrder(@ModelAttribute("order") Order order) {
        orderService.statusUpdate(order);
        return "redirect:/order/editOrder";
    }
}
