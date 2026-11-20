
package com.swipebite.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.restaurant.dto.RestaurantHoursRequest;
import com.swipebite.restaurant.dto.RestaurantHoursResponse;
import com.swipebite.restaurant.entity.RestaurantHours;
import com.swipebite.restaurant.mapper.RestaurantHoursMapper;
import com.swipebite.restaurant.service.RestaurantHoursService;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/hours")
public class RestaurantHoursController {

    private final RestaurantHoursService hoursService;
    private final RestaurantHoursMapper hoursMapper;

    public RestaurantHoursController(
            RestaurantHoursService hoursService,
            RestaurantHoursMapper hoursMapper) {

        this.hoursService = hoursService;
        this.hoursMapper = hoursMapper;
    }

    // Add Restaurant Hours
    @PostMapping
    public ResponseEntity<RestaurantHoursResponse> addHours(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantHoursRequest request) {

        RestaurantHours hours = hoursMapper.toEntity(request);

        RestaurantHours savedHours =
                hoursService.addHours(restaurantId, hours);

        return ResponseEntity.ok(hoursMapper.toResponse(savedHours));
    }

    // Get All Restaurant Hours
    @GetMapping
    public ResponseEntity<List<RestaurantHoursResponse>> getRestaurantHours(
            @PathVariable Long restaurantId) {

        List<RestaurantHoursResponse> response =
                hoursService.getHours(restaurantId)
                .stream()
                .map(hoursMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Update Restaurant Hours
    @PutMapping("/{hoursId}")
    public ResponseEntity<RestaurantHoursResponse> updateHours(
            @PathVariable Long restaurantId,
            @PathVariable Long hoursId,
            @RequestBody RestaurantHoursRequest request) {

        RestaurantHours updatedHours = hoursMapper.toEntity(request);

        RestaurantHours savedHours =
                hoursService.updateHours(restaurantId, hoursId, updatedHours);

        return ResponseEntity.ok(hoursMapper.toResponse(savedHours));
    }

    // Delete Restaurant Hours
    @DeleteMapping("/{hoursId}")
    public ResponseEntity<String> deleteHours(
            @PathVariable Long restaurantId,
            @PathVariable Long hoursId) {

        hoursService.deleteHours(restaurantId, hoursId);

        return ResponseEntity.ok("Restaurant hours deleted successfully");
    }
}
