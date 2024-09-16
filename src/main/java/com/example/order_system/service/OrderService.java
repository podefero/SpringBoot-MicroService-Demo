package com.example.order_system.service;

import com.example.order_system.config.kafka.Topics;
import com.example.order_system.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.order_system.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderModel> getOrdersByCategory(String category) {
        return orderRepository.findByCategory(category);
    }

    public Optional<OrderModel> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Optional<OrderModel> getOrderByName(String name) {
        return orderRepository.findByName(name);
    }

    public OrderModel createOrder(OrderModel orderModel) {
        //Produce kafka message
        kafkaTemplate.send(Topics.ORDER_CREATED, orderModel.toString());
        //save to database
        return orderRepository.save(orderModel);
    }

    public Optional<OrderModel> updateOrder(String id, OrderModel orderModel) {
        return orderRepository.findById(id).map(order -> {
            order.setName(orderModel.getName());
            order.setQuantity(orderModel.getQuantity());
            order.setCategory(orderModel.getCategory());
            order.setOrderStatus(orderModel.getOrderStatus());
            return orderRepository.save(order);
        });
    }

    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
