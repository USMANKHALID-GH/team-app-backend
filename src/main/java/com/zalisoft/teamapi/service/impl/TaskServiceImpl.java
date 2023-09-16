package com.zalisoft.teamapi.service.impl;


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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public Task save(Task taskDto, long userId, long teamId, long projectId) {
        User user =userService.findById(userId);
        Team team=teamService.findById(teamId);
        Project project=projectService.findById(projectId);

        if(StringUtils.isEmpty(taskDto.getName())){
            throw  new BusinessException("");
        }
        if(StringUtils.isEmpty(taskDto.getDescription())){
            throw  new BusinessException("");
        }
       if(StringUtils.isEmpty(taskDto.getStatus().name())){
           throw  new BusinessException("");
       }
       if(StringUtils.isEmpty(taskDto.getDeadline().toString())) {
            throw  new BusinessException("");
        }

        Task task =new Task();
       task.setName(taskDto.getName());
       task.setUser(user);
       task.setTeamId(team.getId());
       task.setDescription(taskDto.getDescription());
       task.setImage(taskDto.getImage());
       task.setDeadline(taskDto.getDeadline());
       task.setStatus(taskDto.getStatus());
       task.setProject(project);
       checkIfTeamCaptainOrProjectManager(task);
       return taskRepository.save(task);
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new BusinessException(""));
    }

    @Override
    public Task findByCurrentUser() {
        return null;
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
           throw  new BusinessException("");

    }

    @Override
    public void delete(long id) {
       Task task= findById(id);
       checkIfTeamCaptainOrProjectManager(task);
       taskRepository.delete(task);
    }

    private  Task updateStatus(Task task,TaskStatus status){
        task.setStatus(status);
       return taskRepository.save(task);
    }
    private void  checkIfTeamCaptainOrProjectManager(Task task){
        User user= userService.findCurrentUser();
        if(!(user.getId().equals(task.getTeam().getCaptain().getId())
        || user.getId().equals(task.getProject().getProjectManager().getId())
        )){
            throw  new BusinessException("");
        }


    }
}
