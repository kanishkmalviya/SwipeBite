
package com.swipebite.order.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.swipebite.order.dto.OrderResponse;
import com.swipebite.order.entity.Order;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setRestaurantId(order.getRestaurantId());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderStatus(order.getOrderStatus());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setCreatedAt(order.getCreatedAt());

        response.setItems(
            order.getItems().stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList())
        );

        return response;
    }
}
