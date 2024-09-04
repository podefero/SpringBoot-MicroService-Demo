package com.example.order_system.service;

import com.example.order_system.config.kafka.Topics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = Topics.ORDER_CREATED)
    public void orderCreatedNotification(Object object) {
        System.out.println("Notification: " + object);
    }
}
