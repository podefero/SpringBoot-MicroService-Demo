package com.example.order_system.service;

import com.example.order_system.config.kafka.Topics;
import com.example.order_system.model.OrderModel;
import com.example.order_system.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final OrderService orderService;
    private final KafkaTemplate<String, OrderModel> kafkaTemplate;

    @Autowired
    public InventoryService(OrderService orderService, KafkaTemplate<String, OrderModel> kafkaTemplate) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = Topics.ORDER_CREATED)
    public void orderCreatedProcess(OrderModel order) {
        //check if order is above quantity 10
        //if so mark status as incomplete
        if(order.getQuantity() > 10) {
            order.setOrderStatus(OrderStatus.INCOMPLETE);
            kafkaTemplate.send(Topics.ORDER_NOTIFIED, order);
        } else {
            order.setOrderStatus(OrderStatus.PROCESSED);
            kafkaTemplate.send(Topics.ORDER_PROCESSED, order);
        }
        orderService.updateOrder(order.getId(), order);
    }
}
