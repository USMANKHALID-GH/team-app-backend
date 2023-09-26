package com.zalisoft.teamapi.service.impl;


import com.zalisoft.teamapi.dto.TaskDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.enums.TaskStatus;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.Task;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.TaskRepository;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.TaskService;
import com.zalisoft.teamapi.service.TeamService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ProjectService projectService;


    @Override
    public Task save(TaskDto taskDto, long userId, long teamId, long projectId) {
        User userToBeAssigned =userService.findById(userId);
        Team team=teamService.findById(teamId);
        Project project=projectService.findById(projectId);

        if(StringUtils.isEmpty(taskDto.getName())){
            throw  new BusinessException(ResponseMessageEnum.BACK_TASK_MSG_001);
        }
        if(StringUtils.isEmpty(taskDto.getDescription())){
            throw  new BusinessException(ResponseMessageEnum.BACK_TASK_MSG_002);
        }
       if(StringUtils.isEmpty(taskDto.getBeginning().toString())){
           throw  new BusinessException(ResponseMessageEnum.BACK_TASK_MSG_003);
       }
       if(StringUtils.isEmpty(taskDto.getDeadline().toString())) {
            throw  new BusinessException(ResponseMessageEnum.BACK_TASK_MSG_004);
        }

        Task task =new Task();
       task.setBeginning(taskDto.getBeginning());
       task.setStatus(TaskStatus.EVALUATION);
       task.setName(taskDto.getName());
       task.setUser(userToBeAssigned);
       task.setTeam(team);
       task.setDescription(taskDto.getDescription());
       task.setImage(taskDto.getImage());
       task.setDeadline(taskDto.getDeadline());
       task.setProject(project);
       checkIfTeamCaptainOrProjectManager(task);
       return taskRepository.save(task);
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum.BACK_TASK_MSG_005));
    }

    @Override
    public List<Task> findByCurrentUser() {
        User user=userService.findCurrentUser();
       return  taskRepository.findByCurrentUser(user.getId());
    }

    @Override
    public Task updateTaskStatusByAdmin(long id,TaskStatus status) {
       Task task= findById(id);
       checkIfTeamCaptainOrProjectManager(task);
        return updateStatus(task,status);
    }

    @Override
    public Task updateTaskStatusByUser(long id, TaskStatus status) {
        User user=userService.findCurrentUser();
        Task task=findById(id);
        if(user.getId().equals(task.getUser().getId())){
            return updateStatus(task,status);
        }
       else
           throw  new BusinessException(ResponseMessageEnum.BACK_CURRENT_USER_MSG_001);

    }

    @Override
    public void delete(long id) {
       Task task= findById(id);
       checkIfTeamCaptainOrProjectManager(task);
       taskRepository.delete(task);
    }

    @Override
    public Page<Task> findTaskByStatus(String status, long pId,Pageable pageable) {
        return taskRepository.findByStatus(status, pId, pageable);
    }

    @Override
    public Page<Task> search(String search, Pageable pageable) {
        log.info("search : {}", search);
        return taskRepository.search(search,pageable);
    }

    private  Task updateStatus(Task task,TaskStatus status){
        task.setStatus(status);
       return taskRepository.save(task);
    }
    private Task checkIfTeamCaptainOrProjectManager(Task task){
        User user= userService.findCurrentUser();
        if(!(user.getId().equals(task.getTeam().getCaptain().getId())
        || user.getId().equals(task.getProject().getProjectManager().getId())
        )){
            throw  new BusinessException(ResponseMessageEnum. BACK_TASK_MSG_006);
        }
        return task;

    }
}
