package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report , Long> {



    @Query("select t from Report t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.personLearning) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.details) like lower(concat('%', :searchText,'%'))")
    Page<Report> search(String searchText, Pageable pageable);

    @Query("from Report r where r.user.id=?1")
    List<Report>  findByCurrentUser(long id);


    @Query("from Report  r where r.team.captain.id=:id and r.createdDate<:days")
    List<Report>  findReportByCaptain(long id, LocalDateTime days);

    @Query("SELECT r FROM Report r WHERE r.isCompleted = false")
    List<Report> findReportsByCompletedFalse();


    @Query(value = " SELECT u.* FROM report as r  " +
            " inner join users as u on r.user_id<>u.id" +
            " where r.deleted=false;", nativeQuery = true)
    List<User> findUserUnsentReport();


    @Query("from Report  as r where r.user.id=:id and  r.isDayOff=true ")
    Optional<Report> existsByDayOffTrueAndUser(long id);



}
