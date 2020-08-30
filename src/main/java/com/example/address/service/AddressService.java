package com.example.address.service;

import com.example.address.entity.Address;
import com.example.address.exception.AddressNotFoundException;
import com.example.address.model.AddressModel;
import com.example.address.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressService {

    private AddressRepository addressRepository;
    private GeographicalCoordinatesService geoService;

    @Autowired
    public AddressService(AddressRepository addressRepository, GeographicalCoordinatesService geoService) {
        this.addressRepository = addressRepository;
        this.geoService = geoService;
    }

    public Long saveAddress(AddressModel addressModel) {
        Address createdAddress = addressRepository.save(handleGeographicalCoordinates(addressModel.toEntity()));
        log.info("Saved address: {}", createdAddress);
        return createdAddress.getId();
    }

    public Long updateOrCreateAddress(Long id, AddressModel addressModel) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
            log.info("Updating address {}", id);
            Address address = addressModel.toEntity();
            address.setId(id);

            return addressRepository.save(handleGeographicalCoordinates(address)).getId();
        }
        return saveAddress(addressModel);
    }

    public void deleteAddress(Long id) throws AddressNotFoundException {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(!optionalAddress.isPresent()) {
            log.info("Address {} not found", id);
            throw new AddressNotFoundException(String.format("Address %s not found", id));
        }
        addressRepository.delete(optionalAddress.get());
        log.info("Deleted address {}", optionalAddress.get());
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public AddressModel getAddressById(Long id) throws AddressNotFoundException {
        Optional<Address> product = addressRepository.findById(id);
        if(!product.isPresent()) {
            log.info("Address {} not found", id);
            throw new AddressNotFoundException(String.format("Address %s not found", id));
        }
        log.info("Found Address: {}", product.get());
        return AddressModel.getAddressModel(product.get());
    }

    private Address handleGeographicalCoordinates(Address address) {
        return geoService.findGeographicalCoordinates(address);
    }
}
