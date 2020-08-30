package com.example.address.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetName;
    private Integer number;
    private String neighbourhood;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String latitude;
    private String longitude;
    private String complement;

    public String formattedAddress() {
        return String.format(
                "%s, %s. %s - %s, %s. %s",
                this.streetName,
                this.number,
                this.city,
                this.state,
                this.country,
                this.zipcode);
    }
}
