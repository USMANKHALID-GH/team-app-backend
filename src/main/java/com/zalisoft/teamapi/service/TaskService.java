package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.enums.TaskStatus;
import com.zalisoft.teamapi.model.Task;

public interface TaskService {
    Task save(Task taskDto, long userId, long teamId , long projectId);

    Task findById(long id);

    Task findByCurrentUser();

    Task updateTaskStatusByAdmin(long id,TaskStatus status);
    Task updateTaskStatusByUser(long id, TaskStatus status);

    void delete(long id);



}
