package com.zalisoft.teamapi.dto;

import lombok.Data;

@Data
public class ParameterDto extends BaseDto{

    private String key;
    private String value;
    private String value2;
    private String description;
}
