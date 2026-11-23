
package com.swipebite.delivery.event;

public class DeliveryEvent {

    private Long deliveryId;
    private Long orderId;
    private Long customerId;
    private Long deliveryPartnerId;
    private String deliveryStatus;

    public DeliveryEvent() {
    }

    public DeliveryEvent(Long deliveryId, Long orderId, Long customerId,
                         Long deliveryPartnerId, String deliveryStatus) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryPartnerId = deliveryPartnerId;
        this.deliveryStatus = deliveryStatus;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDeliveryPartnerId() {
        return deliveryPartnerId;
    }

    public void setDeliveryPartnerId(Long deliveryPartnerId) {
        this.deliveryPartnerId = deliveryPartnerId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
