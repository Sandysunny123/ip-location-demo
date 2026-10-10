package com.sandy.ip_location_demo.service;

import com.sandy.ip_location_demo.dto.GeoResponse;
import com.sandy.ip_location_demo.entity.VisitorLocation;
import com.sandy.ip_location_demo.repo.VisitorLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Instant;

@Service
public class VisitorLocationService {

    private final VisitorLocationRepository repository;
    private final RestClient restClient;

    public VisitorLocationService(
            VisitorLocationRepository repository,
            RestClient restClient) {
        this.repository = repository;
        this.restClient = restClient;
    }

    public GeoResponse saveVisitor(String ip) {

        // Localhost doesn't have a useful public geolocation
        if (ip == null
                || ip.equals("127.0.0.1")
                || ip.equals("::1")
                || ip.equals("0:0:0:0:0:0:0:1")) {

            System.out.println("Local IP detected. Skipping geolocation.");
            return null;
        }
        GeoResponse geo;

        try {
            geo = restClient.get()
                    .uri("https://ipinfo.io/{ip}/json", ip)
                    .retrieve()
                    .body(GeoResponse.class);
        } catch (RestClientException e) {
            System.out.println("Geolocation API failed: " + e.getMessage());
            return null;
        }

        if (geo == null) {
            return null;
        }

        VisitorLocation visitor = new VisitorLocation();

        visitor.setIp(ip);
        visitor.setCountry(geo.country());
        visitor.setState(geo.region());
        visitor.setCity(geo.city());
        visitor.setVisitedAt(Instant.now());

        repository.save(visitor);

        System.out.println(
                "Approximate location: "
                        + geo.city() + ", "
                        + geo.region() + ", "
                        + geo.country()
        );

        return geo;
    }
}