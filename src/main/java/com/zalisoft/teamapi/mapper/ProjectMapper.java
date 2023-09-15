package com.zalisoft.teamapi.mapper;
import com.zalisoft.teamapi.dto.ProjectDto;
import com.zalisoft.teamapi.model.Project;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDto, Project>{
    ProjectDto toDto( Project privilege);
    Project toEntity(ProjectDto projectDto);

    List<Project> toEntity(Set< ProjectDto> projectDtos);

    List<ProjectDto> toDto(Set<Project> projects);
}
