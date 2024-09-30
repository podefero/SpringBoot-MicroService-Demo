package com.example.order_system.serializer;

import com.example.order_system.model.OrderModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class OrderModelDeserializer implements Deserializer<OrderModel> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nothing to configure
    }

    @Override
    public OrderModel deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, OrderModel.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing OrderModel", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}