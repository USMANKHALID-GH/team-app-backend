package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.model.User;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto extends BaseDto{

    private Long id;
    private String image;
    private  String name;
    private UserDto captain;
    private List<UserDto> members;
}
