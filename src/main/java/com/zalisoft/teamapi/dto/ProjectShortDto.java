package com.zalisoft.teamapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectShortDto extends BaseDto {
    private Long id;
    private String name;
    private LocalDate starting;
    private LocalDate deadline;

}
