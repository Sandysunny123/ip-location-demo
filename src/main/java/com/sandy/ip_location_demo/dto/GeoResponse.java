package com.sandy.ip_location_demo.dto;

public record GeoResponse(
        String ip,
        String city,
        String region,
        String country
) {
}