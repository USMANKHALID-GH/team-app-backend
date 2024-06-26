package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.ProjectDto;
import com.zalisoft.teamapi.enums.ProjectStatus;
import com.zalisoft.teamapi.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {

    void save(ProjectDto projectDto , long userId);

    Project updateProjectStatus(long id, ProjectStatus projectStatus);

    Project  findById(long id);

    void extendProjectDeadline(LocalDate localDate , long id);

    List<Project> findUnfinishedProjectThatExceedDeadline();

    List<Project>  findAllUnfinishedProject();

    List<Project> findProjectByStatus(String status);

    Page<Project>  search(Pageable pageable ,String search);


    List<Project>  findByMultipleId(List<Long> ids);

    void delete(long id);

}
