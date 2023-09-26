package com.zalisoft.teamapi.dto;

import lombok.Data;

@Data
public class ReportDto extends BaseDto{

    private Long id;
    private Long projectId;
    private String details;

}
