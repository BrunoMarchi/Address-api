package com.example.address.service;

import com.example.address.configuration.GeocodingApiProperties;
import com.example.address.entity.Address;
import com.example.address.exception.GeographicalCoordinateException;
import com.example.address.model.geocoding.GeocodingResponse;
import com.example.address.model.geocoding.Location;
import com.example.address.model.geocoding.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
public class GeographicalCoordinatesService {

    private RestTemplate restTemplate;
    private GeocodingApiProperties geocodingApiProperties;

    @Autowired
    public GeographicalCoordinatesService(RestTemplate restTemplate, GeocodingApiProperties geocodingApiProperties) {
        this.restTemplate = restTemplate;
        this.geocodingApiProperties = geocodingApiProperties;
    }

    public Address findGeographicalCoordinates(Address address)
            throws GeographicalCoordinateException {
        GeocodingResponse geocodingResponse = consume(buildUri(address));

        return mapToGeographicalCoordinate(address, geocodingResponse);
    }

    private GeocodingResponse consume(String uri) throws GeographicalCoordinateException {
        ResponseEntity<GeocodingResponse> response =
                restTemplate.getForEntity(uri, GeocodingResponse.class);
        if(response.getStatusCode().isError()) {
            throw new GeographicalCoordinateException("Could not find geographical data", response.getStatusCode());
        }
        return response.getBody();
    }

    private String buildUri(Address address) {
        log.info("Searching geographical coordinates for address: {}", address.formattedAddress());
        return UriComponentsBuilder
                .fromHttpUrl(geocodingApiProperties.getBaseUrl())
                .queryParam("address", address.formattedAddress().replaceAll("\\s", "+"))
                .queryParam("key", geocodingApiProperties.getKey())
                .toUriString();
    }

    private Address mapToGeographicalCoordinate(Address address, GeocodingResponse geocodingResponse)
            throws GeographicalCoordinateException {
        List<Result> resultList = geocodingResponse.getResults();
        if(resultList == null || resultList.isEmpty()) {
            throw new GeographicalCoordinateException("Could not find geographical data", HttpStatus.NOT_FOUND);
        }
        Location location = resultList.get(0).getGeometry().getLocation();

        address.setLatitude(location.getLat());
        address.setLongitude(location.getLng());

        return address;
    }
}
