
package com.swipebite.delivery.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.delivery.dto.DeliveryRequest;
import com.swipebite.delivery.dto.DeliveryResponse;
import com.swipebite.delivery.entity.Delivery;

@Component
public class DeliveryMapper {

    public Delivery toEntity(DeliveryRequest request) {

        Delivery delivery = new Delivery();

        delivery.setOrderId(request.getOrderId());
        delivery.setCustomerId(request.getCustomerId());
        delivery.setDeliveryPartnerId(request.getDeliveryPartnerId());
        delivery.setPickupAddress(request.getPickupAddress());
        delivery.setDeliveryAddress(request.getDeliveryAddress());

        return delivery;
    }

    public DeliveryResponse toResponse(Delivery delivery) {

        DeliveryResponse response = new DeliveryResponse();

        response.setId(delivery.getId());
        response.setOrderId(delivery.getOrderId());
        response.setCustomerId(delivery.getCustomerId());
        response.setDeliveryPartnerId(delivery.getDeliveryPartnerId());
        response.setDeliveryStatus(delivery.getDeliveryStatus());
        response.setPickupAddress(delivery.getPickupAddress());
        response.setDeliveryAddress(delivery.getDeliveryAddress());
        response.setAssignedAt(delivery.getAssignedAt());
        response.setDeliveredAt(delivery.getDeliveredAt());
        response.setCreatedAt(delivery.getCreatedAt());
        response.setUpdatedAt(delivery.getUpdatedAt());

        return response;
    }
}
