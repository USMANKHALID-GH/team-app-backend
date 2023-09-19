package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value= "select p.*  from project as p where p.deadline<:date and p.status<>:status " ,nativeQuery = true)
    List<Project> findUnfinishedProjectThatExceedDeadline(LocalDate date, String status);

    @Query(value= "select p.*  from project as p where p.status<>:status " ,nativeQuery = true)
    List<Project>   findAllUnfinishedProject(String status);



    @Query(value = "select p.* from project  p where p.status=?1",nativeQuery = true)
    List<Project>  findByStatusIgnoreCase(String status);


    @Query("select p from Project p " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(p.description) like lower(concat('%', :searchText,'%')) " +
            "       or lower(p.name) like lower(concat('%', :searchText,'%'))" +
            "       or lower(p.status) like lower(concat('%', :searchText,'%'))" +
            "        or lower(p.projectManager.firstName) like lower(concat('%', :searchText,'%'))")
    Page<Project> search(String searchText, Pageable pageable);


}
