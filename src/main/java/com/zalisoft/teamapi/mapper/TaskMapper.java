package com.zalisoft.teamapi.mapper;

import com.zalisoft.teamapi.dto.TaskDto;

import com.zalisoft.teamapi.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDto,Task>{
}
