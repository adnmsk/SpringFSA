package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.repositories.OrderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getByLastNumbers(String lastNumber) {
        return orderRepository.findByLastNumber(lastNumber);
    }

    public void save(Order order) {

        orderRepository.save(order);
    }

    public void statusUpdate(Order order) {

//        return: @Query("update Order o set o.status = ?1 where o.status = ?2")
    }

    public Order findById(int id) {
        return orderRepository.findById(id);
    }
}
