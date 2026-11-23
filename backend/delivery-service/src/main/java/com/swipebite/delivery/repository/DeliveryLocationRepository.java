
package com.swipebite.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swipebite.delivery.entity.DeliveryLocation;

public interface DeliveryLocationRepository
        extends JpaRepository<DeliveryLocation, Long> {

    List<DeliveryLocation> findByDeliveryIdOrderByRecordedAtDesc(Long deliveryId);

    List<DeliveryLocation> findByDeliveryPartnerId(Long deliveryPartnerId);
}
