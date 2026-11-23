
package com.swipebite.order.service;

import com.swipebite.order.event.OrderEventPublisher;
import com.swipebite.order.client.FoodCatalogClient;
import com.swipebite.order.dto.FoodDetailsResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swipebite.order.dto.OrderRequest;
import com.swipebite.order.dto.OrderResponse;
import com.swipebite.order.dto.OrderStatusUpdateRequest;
import com.swipebite.order.entity.Order;
import com.swipebite.order.entity.OrderItem;
import com.swipebite.order.mapper.OrderMapper;
import com.swipebite.order.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderEventPublisher orderEventPublisher;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final FoodCatalogClient foodCatalogClient;

    public OrderService(OrderRepository orderRepository,
            OrderMapper orderMapper,
            OrderEventPublisher orderEventPublisher,
            FoodCatalogClient foodCatalogClient) {

this.orderRepository = orderRepository;
this.orderMapper = orderMapper;
this.orderEventPublisher = orderEventPublisher;
this.foodCatalogClient = foodCatalogClient;
}
    

@Transactional
public OrderResponse createOrder(OrderRequest request) {

    if (request.getItems() == null || request.getItems().isEmpty()) {
        throw new IllegalArgumentException(
                "Order must contain at least one food item");
    }

    Order order = new Order();

    order.setCustomerId(request.getCustomerId());
    order.setRestaurantId(request.getRestaurantId());
    order.setDeliveryAddress(request.getDeliveryAddress());
    order.setOrderStatus("PLACED");
    order.setPaymentStatus("PENDING");

    BigDecimal totalAmount = BigDecimal.ZERO;

    for (var itemRequest : request.getItems()) {

        if (itemRequest.getFoodId() == null) {
            throw new IllegalArgumentException("Food ID is required");
        }

        if (itemRequest.getQuantity() == null
                || itemRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        FoodDetailsResponse food =
                foodCatalogClient.getFoodById(itemRequest.getFoodId());

        if (!food.isAvailable()) {
            throw new IllegalArgumentException(
                    food.getFoodName() + " is not available");
        }

        if (!food.getRestaurantId().equals(request.getRestaurantId())) {
            throw new IllegalArgumentException(
                    "All food items must belong to the same restaurant");
        }

        OrderItem item = new OrderItem();

        item.setFoodId(food.getId());
        item.setFoodName(food.getFoodName());
        item.setPrice(food.getPrice());
        item.setQuantity(itemRequest.getQuantity());

        BigDecimal subtotal = food.getPrice().multiply(
                BigDecimal.valueOf(itemRequest.getQuantity()));

        item.setSubtotal(subtotal);
        item.setOrder(order);

        order.getItems().add(item);

        totalAmount = totalAmount.add(subtotal);
    }

    order.setTotalAmount(totalAmount);

    Order savedOrder = orderRepository.save(order);

    orderEventPublisher.publishOrderCreated(savedOrder);

    return orderMapper.toResponse(savedOrder);
}
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getCustomerOrders(Long customerId) {

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getRestaurantOrders(Long restaurantId) {

        return orderRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long id,
                                           OrderStatusUpdateRequest request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(request.getOrderStatus());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toResponse(updatedOrder);
    }

    public void cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("DELIVERED".equalsIgnoreCase(order.getOrderStatus())) {
            throw new IllegalArgumentException("Delivered order cannot be cancelled");
        }

        order.setOrderStatus("CANCELLED");

        orderRepository.save(order);
    }
}
