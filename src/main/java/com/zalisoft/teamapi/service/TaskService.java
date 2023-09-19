package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.TaskDto;
import com.zalisoft.teamapi.enums.TaskStatus;
import com.zalisoft.teamapi.model.Task;
import com.zalisoft.teamapi.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskService {
    Task save(TaskDto taskDto, long userId, long teamId , long projectId);

    Task findById(long id);

    List<Task> findByCurrentUser();

    Task updateTaskStatusByAdmin(long id,TaskStatus status);
    Task updateTaskStatusByUser(long id, TaskStatus status);

    void delete(long id);

    Page<Task>    findTaskByStatus(String status,long pId, Pageable pageable);


    Page<Task> search(String search, Pageable pageable);





}
