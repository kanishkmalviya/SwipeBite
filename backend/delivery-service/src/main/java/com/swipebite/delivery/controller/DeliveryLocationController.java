
package com.swipebite.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.delivery.dto.DeliveryLocationRequest;
import com.swipebite.delivery.dto.DeliveryLocationResponse;
import com.swipebite.delivery.service.DeliveryLocationService;

@RestController
@RequestMapping("/api/delivery-locations")
public class DeliveryLocationController {

    private final DeliveryLocationService locationService;

    public DeliveryLocationController(DeliveryLocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<DeliveryLocationResponse> updateLocation(
            @RequestBody DeliveryLocationRequest request) {

        return ResponseEntity.ok(locationService.updateLocation(request));
    }

    @GetMapping("/{deliveryId}/history")
    public ResponseEntity<List<DeliveryLocationResponse>> getLocationHistory(
            @PathVariable Long deliveryId) {

        return ResponseEntity.ok(
                locationService.getDeliveryLocationHistory(deliveryId));
    }

    @GetMapping("/{deliveryId}/latest")
    public ResponseEntity<DeliveryLocationResponse> getLatestLocation(
            @PathVariable Long deliveryId) {

        return ResponseEntity.ok(
                locationService.getLatestLocation(deliveryId));
    }
}
