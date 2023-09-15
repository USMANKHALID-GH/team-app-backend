package com.zalisoft.teamapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "town")
    private String town;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "street_code")
    private String streetCode;
    @Column(name = "house_no")
    private String houseNumber;
    @Column(name = "address_description")
    private String descriptions;
}
