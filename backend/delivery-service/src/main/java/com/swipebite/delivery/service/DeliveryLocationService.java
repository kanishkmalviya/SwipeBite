
package com.swipebite.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swipebite.delivery.dto.DeliveryLocationRequest;
import com.swipebite.delivery.dto.DeliveryLocationResponse;
import com.swipebite.delivery.entity.DeliveryLocation;
import com.swipebite.delivery.exception.ResourceNotFoundException;
import com.swipebite.delivery.mapper.DeliveryLocationMapper;
import com.swipebite.delivery.repository.DeliveryLocationRepository;
import com.swipebite.delivery.repository.DeliveryRepository;

@Service
public class DeliveryLocationService {

    private final DeliveryLocationRepository locationRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryLocationMapper locationMapper;

    public DeliveryLocationService(
            DeliveryLocationRepository locationRepository,
            DeliveryRepository deliveryRepository,
            DeliveryLocationMapper locationMapper) {

        this.locationRepository = locationRepository;
        this.deliveryRepository = deliveryRepository;
        this.locationMapper = locationMapper;
    }

    public DeliveryLocationResponse updateLocation(DeliveryLocationRequest request) {

        if (request.getLatitude() == null || request.getLongitude() == null) {
            throw new IllegalArgumentException("Latitude and longitude are required");
        }

        if (request.getLatitude() < -90 || request.getLatitude() > 90
                || request.getLongitude() < -180 || request.getLongitude() > 180) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }

        deliveryRepository.findById(request.getDeliveryId())
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        DeliveryLocation location = locationMapper.toEntity(request);

        DeliveryLocation savedLocation = locationRepository.save(location);

        return locationMapper.toResponse(savedLocation);
    }

    public List<DeliveryLocationResponse> getDeliveryLocationHistory(Long deliveryId) {

        deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return locationRepository
                .findByDeliveryIdOrderByRecordedAtDesc(deliveryId)
                .stream()
                .map(locationMapper::toResponse)
                .toList();
    }

    public DeliveryLocationResponse getLatestLocation(Long deliveryId) {

        deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        List<DeliveryLocation> locations = locationRepository
                .findByDeliveryIdOrderByRecordedAtDesc(deliveryId);

        if (locations.isEmpty()) {
            throw new ResourceNotFoundException("Location not available");
        }

        return locationMapper.toResponse(locations.get(0));
    }
}
