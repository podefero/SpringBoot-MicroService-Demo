package com.example.order_system.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order")
@Jacksonized
@Builder
@AllArgsConstructor
@Data
public class OrderModel {
    @Id
    @Schema(hidden = true) // hides this in swagger
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private int quantity;

    private String category;

    private OrderStatus orderStatus;
}
