package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.enums.UserType;
import lombok.Data;

@Data
public class UserShortDto extends BaseDto{

    private Long id;
    private String firstName;
    private  String lastName;
    private String  email;

}
