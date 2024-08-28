package com.example.order_system.repository;

import com.example.order_system.model.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    // Find an order by name
    Optional<OrderModel> findByName(String name);

    // Find all orders in a specific category and return name and quantity
    @Query(value = "{ 'category': ?0 }", fields = "{ 'name': 1, 'quantity': 1, '_id': 0 }")
    List<OrderModel> findByCategory(String category);

    //List<OrderModel> findByCategory(String category, Pageable pageable);
}
