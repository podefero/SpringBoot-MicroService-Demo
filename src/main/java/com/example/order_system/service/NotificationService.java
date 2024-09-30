package com.example.order_system.service;

import com.example.order_system.config.kafka.Topics;
import com.example.order_system.model.OrderModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = Topics.ORDER_NOTIFIED)
    public void orderProcessedNotification(OrderModel object) {
        System.out.println("Notification " + object);
    }
}
