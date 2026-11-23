
package com.swipebite.swipe.service;

import java.util.List;
import java.util.stream.Collectors;

import com.swipebite.swipe.event.SwipeEventPublisher;
import org.springframework.stereotype.Service;

import com.swipebite.swipe.dto.SwipeRequest;
import com.swipebite.swipe.dto.SwipeResponse;
import com.swipebite.swipe.entity.Swipe;
import com.swipebite.swipe.mapper.SwipeMapper;
import com.swipebite.swipe.repository.SwipeRepository;

@Service
public class SwipeService {

    private final SwipeRepository swipeRepository;
    private final SwipeMapper swipeMapper;
    private final SwipeEventPublisher swipeEventPublisher;

    public SwipeService(
            SwipeRepository swipeRepository,
            SwipeMapper swipeMapper,
            SwipeEventPublisher swipeEventPublisher) {

        this.swipeRepository = swipeRepository;
        this.swipeMapper = swipeMapper;
        this.swipeEventPublisher = swipeEventPublisher;
    }  
    
    

public SwipeResponse createSwipe(SwipeRequest request) {

    if (!"RIGHT".equalsIgnoreCase(request.getSwipeDirection())
            && !"LEFT".equalsIgnoreCase(request.getSwipeDirection())) {

        throw new IllegalArgumentException(
                "Swipe direction must be RIGHT or LEFT");
    }

    if (swipeRepository.existsByCustomerIdAndFoodId(
            request.getCustomerId(), request.getFoodId())) {

        throw new IllegalArgumentException(
                "This food has already been swiped by this customer");
    }

    Swipe swipe = swipeMapper.toEntity(request);

    swipe.setSwipeDirection(
            request.getSwipeDirection().toUpperCase());

    Swipe savedSwipe = swipeRepository.save(swipe);

    // Publish Kafka Event
    swipeEventPublisher.publishSwipeCreated(savedSwipe);

    return swipeMapper.toResponse(savedSwipe);
}
    public SwipeResponse getSwipeById(Long id) {

        Swipe swipe = swipeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Swipe not found"));

        return swipeMapper.toResponse(swipe);
    }

    public List<SwipeResponse> getCustomerSwipes(Long customerId) {

        return swipeRepository.findByCustomerId(customerId)
                .stream()
                .map(swipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SwipeResponse> getSelectedFoods(Long customerId) {

        return swipeRepository
                .findByCustomerIdAndSwipeDirection(customerId, "RIGHT")
                .stream()
                .map(swipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SwipeResponse> getSkippedFoods(Long customerId) {

        return swipeRepository
                .findByCustomerIdAndSwipeDirection(customerId, "LEFT")
                .stream()
                .map(swipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteSwipe(Long id) {

        Swipe swipe = swipeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Swipe not found"));

        swipeRepository.delete(swipe);
    }
}
