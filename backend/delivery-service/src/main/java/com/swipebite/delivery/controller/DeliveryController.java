
package com.swipebite.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.delivery.dto.DeliveryRequest;
import com.swipebite.delivery.dto.DeliveryResponse;
import com.swipebite.delivery.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(
            @RequestBody DeliveryRequest request) {

        return ResponseEntity.ok(deliveryService.createDelivery(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliveryResponse> getDeliveryByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(deliveryService.getDeliveryByOrderId(orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<DeliveryResponse>> getCustomerDeliveries(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(deliveryService.getCustomerDeliveries(customerId));
    }

    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<DeliveryResponse>> getPartnerDeliveries(
            @PathVariable Long partnerId) {

        return ResponseEntity.ok(deliveryService.getPartnerDeliveries(partnerId));
    }

    @PutMapping("/{id}/assign/{partnerId}")
    public ResponseEntity<DeliveryResponse> assignDeliveryPartner(
            @PathVariable Long id,
            @PathVariable Long partnerId) {

        return ResponseEntity.ok(
                deliveryService.assignDeliveryPartner(id, partnerId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                deliveryService.updateDeliveryStatus(id, status));
    }
}
