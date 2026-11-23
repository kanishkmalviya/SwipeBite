
package com.swipebite.swipe.dto;

import java.util.List;

public class SwipeHistoryResponse {

    private Long customerId;
    private List<SwipeResponse> swipes;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<SwipeResponse> getSwipes() {
        return swipes;
    }

    public void setSwipes(List<SwipeResponse> swipes) {
        this.swipes = swipes;
    }
}
