
package com.swipebite.delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swipebite.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByCustomerId(Long customerId);

    List<Delivery> findByDeliveryPartnerId(Long deliveryPartnerId);
}
