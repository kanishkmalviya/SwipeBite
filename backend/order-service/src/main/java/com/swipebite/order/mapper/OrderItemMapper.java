
package com.swipebite.order.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.order.dto.OrderItemResponse;
import com.swipebite.order.entity.OrderItem;

@Component
public class OrderItemMapper {

    public OrderItemResponse toResponse(OrderItem item) {

        OrderItemResponse response = new OrderItemResponse();

        response.setId(item.getId());
        response.setFoodId(item.getFoodId());
        response.setFoodName(item.getFoodName());
        response.setPrice(item.getPrice());
        response.setQuantity(item.getQuantity());
        response.setSubtotal(item.getSubtotal());

        return response;
    }
}
