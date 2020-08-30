package com.example.address.controller;

import com.example.address.entity.Address;
import com.example.address.exception.AddressNotFoundException;
import com.example.address.model.AddressModel;
import com.example.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Create Address")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAddress(
            @Parameter(description = "Address to be created", required = true)
            @RequestBody AddressModel addressModel) throws URISyntaxException {
        log.info("Received request to create address: {}", addressModel);
        Long id = addressService.saveAddress(addressModel);
        return ResponseEntity
                .ok(new URI(id.toString()));
    }

    @Operation(
            summary = "Update Address",
            description = "Update Address, if it doesn't exist then it'll be created"
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrCreateAddress(
            @Parameter(description = "ID of the Address", required = true)
            @PathVariable Long id,
            @Parameter(description = "Address to be updated", required = true)
            @RequestBody AddressModel addressModel) throws URISyntaxException {
        log.info("Received request to update address {}", id);
        Long addressId = addressService.updateOrCreateAddress(id, addressModel);
        return ResponseEntity
                .ok(new URI(addressId.toString()));
    }

    @Operation(summary = "Delete Address")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @Parameter(description = "ID of the Address", required = true)
            @PathVariable Long id) throws AddressNotFoundException {
        log.info("Received request to delete address {}", id);
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Address by ID")
    @GetMapping("/{id}")
    @ResponseBody
    public AddressModel getAddressById(
            @Parameter(description = "ID of the Address", required = true)
            @PathVariable Long id) throws AddressNotFoundException {
        log.info("Received request to get address {}", id);
        return addressService.getAddressById(id);
    }

    @Operation(summary = "List all Addresses")
    @GetMapping
    @ResponseBody
    public List<Address> getAllAddresss() {
        log.info("Received request to get all address");
        return addressService.getAllAddresses();
    }
}
