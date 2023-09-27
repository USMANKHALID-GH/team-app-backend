package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.CaptainReport;
import com.zalisoft.teamapi.model.DailyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CaptainReportRepository extends JpaRepository<CaptainReport,Long> {


    @Query("select t from CaptainReport t " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(t.user.lastName) like lower(concat('%', :searchText,'%')) " +
            "       or lower(t.user.firstName) like lower(concat('%', :searchText,'%'))" +
            "  OR EXISTS (SELECT r FROM t.reports r WHERE LOWER(r.project.name) LIKE LOWER(CONCAT('%', :searchText, '%'))" +
            "   or lower(r.details) like lower(concat('%', :searchText,'%')))")
    Page<CaptainReport> search(String searchText, Pageable pageable);


    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM CaptainReport e WHERE " +
            "  FUNCTION('DATE', e.createdDate) = :today AND e.user.id=:id")
    boolean existsByCreatedDate(LocalDate today,long id);

    @Query("from  CaptainReport  c where  FUNCTION('DATE',c.createdDate)=:date AND c.user.id=:id")
    List<CaptainReport>  find(LocalDate date,long id);
}
