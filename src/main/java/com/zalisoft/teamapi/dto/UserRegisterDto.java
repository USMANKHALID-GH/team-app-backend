package com.zalisoft.teamapi.dto;


import com.zalisoft.teamapi.enums.WorkType;
import com.zalisoft.teamapi.model.Address;
import lombok.Data;

@Data
public class UserRegisterDto extends BaseDto{

    private String firstName;
    private  String lastName;
    private String  email;
    private  String phone;
    private WorkType title;
    private String image;
    private int experience;
    private String tc;
    private Address address;
    private  String password;


}
