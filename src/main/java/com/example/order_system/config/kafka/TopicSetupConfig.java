package com.example.order_system.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicSetupConfig {
    //this will create topic on broker
    @Bean
    public NewTopic orderCratedTopic() {
        return TopicBuilder.name(Topics.ORDER_CREATED)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderNotifiedTopic() {
        return TopicBuilder.name(Topics.ORDER_NOTIFIED)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderProcessedTopic() {
        return TopicBuilder.name(Topics.ORDER_PROCESSED)
                .partitions(10)
                .replicas(1)
                .build();
    }

}
