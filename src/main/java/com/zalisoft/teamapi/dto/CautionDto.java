package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.model.User;
import lombok.Data;
import java.util.List;

@Data
public class CautionDto extends BaseDto{

    private Long id;
    private String name;
    private Long issuedUserId;
    private String message;
    private List<UserShortDto> user;
}
