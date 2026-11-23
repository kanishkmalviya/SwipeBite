
package com.swipebite.swipe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.swipe.dto.SwipeRequest;
import com.swipebite.swipe.dto.SwipeResponse;
import com.swipebite.swipe.service.SwipeService;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    private final SwipeService swipeService;

    public SwipeController(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    @PostMapping
    public ResponseEntity<SwipeResponse> createSwipe(
            @RequestBody SwipeRequest request) {
        return ResponseEntity.ok(swipeService.createSwipe(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwipeResponse> getSwipeById(
            @PathVariable Long id) {
        return ResponseEntity.ok(swipeService.getSwipeById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SwipeResponse>> getCustomerSwipes(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                swipeService.getCustomerSwipes(customerId));
    }

    @GetMapping("/customer/{customerId}/selected")
    public ResponseEntity<List<SwipeResponse>> getSelectedFoods(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                swipeService.getSelectedFoods(customerId));
    }

    @GetMapping("/customer/{customerId}/skipped")
    public ResponseEntity<List<SwipeResponse>> getSkippedFoods(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                swipeService.getSkippedFoods(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSwipe(
            @PathVariable Long id) {
        swipeService.deleteSwipe(id);
        return ResponseEntity.ok("Swipe deleted successfully");
    }
}
