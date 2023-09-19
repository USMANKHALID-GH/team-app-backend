package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {


    @Query("from Task  as t where t.user.id=:id")
   List<Task>  findByCurrentUser(Long id);

    @Query(value = "select t.* from task as t where t.status=:status and t.project_id=:pId", nativeQuery = true)
    Page<Task> findByStatus(String status, long pId, Pageable pageable);


    @Query("select t from Task t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.name) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.description) like lower(concat('%', :searchText,'%'))" +
            "       or lower(t.status) like lower(concat('%', :searchText,'%'))" )
    Page<Task> search(String searchText, Pageable pageable);

}
