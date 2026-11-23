
package com.swipebite.delivery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swipebite.delivery.dto.DeliveryRequest;
import com.swipebite.delivery.dto.DeliveryResponse;
import com.swipebite.delivery.entity.Delivery;
import com.swipebite.delivery.event.DeliveryEventPublisher;
import com.swipebite.delivery.exception.ResourceNotFoundException;
import com.swipebite.delivery.mapper.DeliveryMapper;
import com.swipebite.delivery.repository.DeliveryRepository;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    private final DeliveryEventPublisher deliveryEventPublisher;
    
    public DeliveryService(DeliveryRepository deliveryRepository,
            DeliveryMapper deliveryMapper,
            DeliveryEventPublisher deliveryEventPublisher) {

this.deliveryRepository = deliveryRepository;
this.deliveryMapper = deliveryMapper;
this.deliveryEventPublisher = deliveryEventPublisher;
}

    public DeliveryResponse createDelivery(DeliveryRequest request) {

        if (request.getOrderId() == null || request.getCustomerId() == null) {
            throw new IllegalArgumentException("Order ID and Customer ID are required");
        }

        if (deliveryRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new IllegalArgumentException("Delivery already exists for this order");
        }

        Delivery delivery = deliveryMapper.toEntity(request);
        delivery.setDeliveryStatus("PENDING");

        if (request.getDeliveryPartnerId() != null) {
            delivery.setDeliveryStatus("ASSIGNED");
            delivery.setAssignedAt(LocalDateTime.now());
        }

        Delivery savedDelivery = deliveryRepository.save(delivery);

        deliveryEventPublisher.publishDeliveryEvent(savedDelivery);


        return deliveryMapper.toResponse(savedDelivery);
    }
    
    
    

    public DeliveryResponse getDeliveryById(Long id) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return deliveryMapper.toResponse(delivery);
    }
    
    
    
    

    public DeliveryResponse getDeliveryByOrderId(Long orderId) {

        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return deliveryMapper.toResponse(delivery);
    }

    public List<DeliveryResponse> getCustomerDeliveries(Long customerId) {

        return deliveryRepository.findByCustomerId(customerId)
                .stream()
                .map(deliveryMapper::toResponse)
                .toList();
    }

    public List<DeliveryResponse> getPartnerDeliveries(Long partnerId) {

        return deliveryRepository.findByDeliveryPartnerId(partnerId)
                .stream()
                .map(deliveryMapper::toResponse)
                .toList();
    }

    public DeliveryResponse assignDeliveryPartner(Long id, Long partnerId) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        if (partnerId == null) {
            throw new IllegalArgumentException("Delivery partner ID is required");
        }

        delivery.setDeliveryPartnerId(partnerId);
        delivery.setDeliveryStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());

        Delivery updatedDelivery = deliveryRepository.save(delivery);

        return deliveryMapper.toResponse(updatedDelivery);
    }

    public DeliveryResponse updateDeliveryStatus(Long id, String status) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        if (!List.of("PICKED_UP", "OUT_FOR_DELIVERY", "DELIVERED", "FAILED")
                .contains(status)) {
            throw new IllegalArgumentException("Invalid delivery status");
        }

        delivery.setDeliveryStatus(status);

        if (status.equals("DELIVERED")) {
            delivery.setDeliveredAt(LocalDateTime.now());
        }

        Delivery updatedDelivery = deliveryRepository.save(delivery);

        return deliveryMapper.toResponse(updatedDelivery);
    }
}
