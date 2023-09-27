package com.zalisoft.teamapi.dto;

import com.zalisoft.teamapi.model.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PermissionDto extends BaseDto{
    private Long id;
    private String reason;
    private boolean isApproved;
    private User approvedBy;
    private LocalDate starting;
    private LocalDate  expiryDate;
}
