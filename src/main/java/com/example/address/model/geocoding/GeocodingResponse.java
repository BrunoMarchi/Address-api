package com.example.address.model.geocoding;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GeocodingResponse {
    private List<Result> results;
}
