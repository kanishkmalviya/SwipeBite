
package com.swipebite.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.swipebite.order.dto.FoodDetailsResponse;

@FeignClient(name = "food-catalog-service")
public interface FoodCatalogClient {

    @GetMapping("/api/foods/{id}")
    FoodDetailsResponse getFoodById(@PathVariable("id") Long id);
}
