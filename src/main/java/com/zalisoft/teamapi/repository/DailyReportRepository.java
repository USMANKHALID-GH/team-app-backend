package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.DailyReport;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {



    @Query("select t from DailyReport t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.personLearning) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.details) like lower(concat('%', :searchText,'%'))")
    Page<DailyReport> search(String searchText, Pageable pageable);

    @Query("from DailyReport r where r.user.id=?1")
    List<DailyReport>  findByCurrentUser(long id);


    @Query("from DailyReport  r where r.team.captain.id=:id and r.createdDate<:days")
    List<DailyReport>  findReportByCaptain(long id, LocalDateTime days);

    @Query("SELECT r FROM DailyReport r WHERE r.isCompleted = false")
    List<DailyReport> findReportsByCompletedFalse();


    @Query( "SELECT R.user FROM DailyReport  AS R WHERE R.isDayOff=true  AND R.deleted=FALSE")
    List<User> findAllUserOnDayOff();

    @Query("SELECT R.user FROM DailyReport  R WHERE R.user.id=:userId  AND R.deleted=FALSE AND R.isDayOff=true ")
    Optional<User>   checkIfOnDayOff(long userId);



    @Transactional
    @Modifying
    @Query(value = "UPDATE report  SET day_off = false WHERE day_off = true AND deleted = false", nativeQuery = true)
    void updateIsDayOffToFalse();







}
