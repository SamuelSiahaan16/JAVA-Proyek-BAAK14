package com.baak14.javabaak14.controller;

import com.baak14.javabaak14.model.Order;
import com.baak14.javabaak14.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable int orderId) {
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        Map<String, Object> response = new HashMap<>();

        if (optionalOrder.isPresent()) {
            response.put("status", "success");
            response.put("data", optionalOrder.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", "error");
            response.put("message", "Order not found with id: " + orderId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", createdOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable int orderId, @RequestBody Order updatedOrder) {
        Order updated = orderService.updateOrder(orderId, updatedOrder);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Order deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        try {
            List<Order> orderList = orderService.getOrdersByUserId(userId);
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions, e.g., log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     
}
