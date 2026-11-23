
package com.swipebite.delivery.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.delivery.dto.DeliveryLocationRequest;
import com.swipebite.delivery.dto.DeliveryLocationResponse;
import com.swipebite.delivery.entity.DeliveryLocation;

@Component
public class DeliveryLocationMapper {

    public DeliveryLocation toEntity(DeliveryLocationRequest request) {

        DeliveryLocation location = new DeliveryLocation();

        location.setDeliveryId(request.getDeliveryId());
        location.setDeliveryPartnerId(request.getDeliveryPartnerId());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());

        return location;
    }

    public DeliveryLocationResponse toResponse(DeliveryLocation location) {

        DeliveryLocationResponse response = new DeliveryLocationResponse();

        response.setId(location.getId());
        response.setDeliveryId(location.getDeliveryId());
        response.setDeliveryPartnerId(location.getDeliveryPartnerId());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setRecordedAt(location.getRecordedAt());

        return response;
    }
}
