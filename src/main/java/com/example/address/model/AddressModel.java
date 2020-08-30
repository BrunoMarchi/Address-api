package com.example.address.model;

import com.example.address.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Data
@Builder
@AllArgsConstructor
public class AddressModel {
    @NonNull
    private String streetName;

    @NonNull
    private Integer number;

    @NonNull
    private String neighbourhood;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String country;

    @NonNull
    private String zipcode;

    private String complement;
    private String latitude;
    private String longitude;

    public static AddressModel getAddressModel(Address address) {
        MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

        return mapper.map(address, AddressModel.class);
    }

    public Address toEntity() {
        MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

        return mapper.map(this, Address.class);
    }
}
