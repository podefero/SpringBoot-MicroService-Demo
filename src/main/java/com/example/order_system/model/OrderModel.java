package com.example.order_system.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order")
@AllArgsConstructor
@Data
public class OrderModel {
    @Id
    @Schema(hidden = true) //hides this in swagger
    private String id;


    private String name;
    private int quantity;
    private String category;
    private OrderStatus orderStatus;
}
