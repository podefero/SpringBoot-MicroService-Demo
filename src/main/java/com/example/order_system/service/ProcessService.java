package com.example.order_system.service;

import com.example.order_system.config.kafka.Topics;
import com.example.order_system.model.OrderModel;
import com.example.order_system.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private final OrderService orderService;
    private final KafkaTemplate<String, OrderModel> kafkaTemplate;

    @Autowired
    public ProcessService(OrderService orderService, KafkaTemplate<String, OrderModel> kafkaTemplate) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = Topics.ORDER_PROCESSED)
    public void orderProcessNotify(OrderModel order) {
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderService.updateOrder(order.getId(), order);
        kafkaTemplate.send(Topics.ORDER_NOTIFIED, order);
    }
}
