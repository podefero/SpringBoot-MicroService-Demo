package com.example.order_system.controller;

import com.example.order_system.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.order_system.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //get all orders
    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders();
    }

    //Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable String id) {
        Optional<OrderModel> orderModel = orderService.getOrderById(id);
        return orderModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Get order by name
    @GetMapping("/name/{name}")
    public ResponseEntity<OrderModel> getOrderByName(@PathVariable String name) {
        Optional<OrderModel> orderModel = orderService.getOrderByName(name);
        return orderModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create Order
    @PostMapping
    public OrderModel createOrder(@RequestBody OrderModel orderModel) {
        return orderService.createOrder(orderModel);
    }


}
