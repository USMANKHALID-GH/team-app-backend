package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.ProjectDto;
import com.zalisoft.teamapi.enums.ProjectStatus;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.ProjectRepository;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.zalisoft.teamapi.enums.ProjectStatus.NOT_STARTED;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRepository projectRepository;



    @Override
    public void save(ProjectDto projectDto, long id) {
        if(StringUtils.isEmpty(projectDto.getName())){
          throw  new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_001);
        }
        if(ObjectUtils.isEmpty(projectDto.getDeadline())){
            throw  new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_002);
        }

        if(ObjectUtils.isEmpty(projectDto.getStarting())){
            throw  new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_002);
        }

        Project project=new Project();
        User user=userService.findById(id);
        project.setStatus(NOT_STARTED);
        project.setProjectManager(user);
        project.setDeadline(projectDto.getDeadline());
        project.setStarting(projectDto.getStarting());
        project.setBudget(projectDto.getBudget());
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        projectRepository.save(project);
    }

    @Override
    public Project updateProjectStatus(long id, ProjectStatus projectStatus) {
        User user=userService.findCurrentUser();
        Project project= findById(id);
        if(project.getProjectManager().getId().equals(user.getId())){
       project.setStatus(projectStatus);
       return projectRepository.save(project);}
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_005);
    }

    @Override
    public Project findById(long id) {
        return projectRepository.findById(id)
                .orElseThrow(()->new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_004));
    }

    @Override
    public void extendProjectDeadline(LocalDate localDate, long id) {
        User user=userService.findCurrentUser();
        Project project= findById(id);
        if(project.getProjectManager().getId().equals(user.getId())){
            project.setDeadline(localDate);
            projectRepository.save(project);
        }
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_PROJECT_MSG_005);

    }

    @Override
    public List<Project> findUnfinishedProject() {
        final  LocalDate date = LocalDate.now();
        return projectRepository.findUnfinishedProject(date,ProjectStatus.FINISHED.name());
    }

    @Override
    public List<Project> findProjectByStatus(String status) {
        return projectRepository.findByStatusIgnoreCase(status);
    }

    @Override
    public Page<Project> search(Pageable pageable, String search) {
        return projectRepository.search(search,pageable);
    }

}
