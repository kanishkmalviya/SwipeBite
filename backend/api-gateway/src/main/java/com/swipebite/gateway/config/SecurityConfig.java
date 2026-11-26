
package com.swipebite.gateway.config;

import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.swipebite.gateway.filter.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                // Public APIs
                .requestMatchers(
                    "/api/auth/**",
                    "/api/users/register",
                    "/api/users/register/restaurant",
                    "/api/users/register/delivery-partner"
                ).permitAll()

                // Customer APIs
                .requestMatchers("/api/users/**")
                .hasRole("CUSTOMER")

                // Restaurant APIs
                .requestMatchers(HttpMethod.GET, "/api/restaurants/**")
                .hasAnyRole("CUSTOMER", "RESTAURANT")

                .requestMatchers(HttpMethod.POST, "/api/restaurants/**")
                .hasRole("RESTAURANT")

                .requestMatchers(HttpMethod.PUT, "/api/restaurants/**")
                .hasRole("RESTAURANT")

                .requestMatchers(HttpMethod.DELETE, "/api/restaurants/**")
                .hasRole("RESTAURANT")

                // Delivery APIs
                .requestMatchers(HttpMethod.GET, "/api/deliveries/customer/**")
                .hasRole("CUSTOMER")

                .requestMatchers(HttpMethod.GET, "/api/deliveries/partner/**")
                .hasRole("DELIVERY_PARTNER")

                .requestMatchers(HttpMethod.GET, "/api/deliveries/order/**")
                .hasAnyRole("CUSTOMER", "RESTAURANT", "DELIVERY_PARTNER")

                .requestMatchers(HttpMethod.GET, "/api/deliveries/*")
                .hasAnyRole("CUSTOMER", "RESTAURANT", "DELIVERY_PARTNER")

                .requestMatchers(HttpMethod.POST, "/api/deliveries")
                .hasRole("RESTAURANT")

                .requestMatchers(HttpMethod.PUT, "/api/deliveries/*/assign/**")
                .hasRole("RESTAURANT")

                .requestMatchers(HttpMethod.PUT, "/api/deliveries/*/status")
                .hasRole("DELIVERY_PARTNER")

                // Payment APIs - CUSTOMER only
                .requestMatchers("/api/payments/**")
                .hasRole("CUSTOMER")

                // Order APIs
                .requestMatchers(HttpMethod.PUT, "/api/orders/*/status")
                .hasRole("RESTAURANT")

                // Swipe APIs
                .requestMatchers("/api/swipes/**")
                .hasRole("CUSTOMER")

                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
