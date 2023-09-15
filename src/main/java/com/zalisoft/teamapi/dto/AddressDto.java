package com.zalisoft.teamapi.dto;

import lombok.Data;

@Data
public class AddressDto extends BaseDto{
    private String country;
    private String city;
    private String town;
    private String streetName;
    private String streetCode;
    private String houseNumber;
    private String descriptions;
}
