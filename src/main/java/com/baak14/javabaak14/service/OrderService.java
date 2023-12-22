package com.baak14.javabaak14.service;

import com.baak14.javabaak14.model.Order;
import com.baak14.javabaak14.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BarangService barangService;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, BarangService barangService, UserService userService) {
        this.orderRepository = orderRepository;
        this.barangService = barangService;
        this.userService = userService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(int orderId, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);

        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setBarang(updatedOrder.getBarang());
            existingOrder.setUser(updatedOrder.getUser());
            existingOrder.setTotal(updatedOrder.getTotal());

            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order createOrderFromJson(String orderJson) {
        Order order = jsonToOrder(orderJson);

        // Set nilai Barang dan User pada Order
        order.setBarang(barangService.getBarangById(order.getBarangId()));
        order.setUser(userService.getUserById(order.getUserId()));

        // Simpan order
        return createOrder(order);
    }

    private Order jsonToOrder(String orderJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(orderJson, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON to Order", e);
        }
    } 
    
    public List<Order> getOrdersByUserId(Integer userId) {
        // Implement the logic to fetch orders by user ID from the repository
        return orderRepository.findByUserId(userId);
    }
}
