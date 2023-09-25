package com.zalisoft.teamapi.repository;

import com.zalisoft.teamapi.model.CaptainReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptainReportRepository extends JpaRepository<CaptainReport,Long> {


    @Query("select c from CaptainReport c " +
            " where '' = :searchText or :searchText is null " +
            "       or lower(c.description) like lower(concat('%', :searchText,'%')) " +
            "       or lower(c.user.lastName) like lower(concat('%', :searchText,'%'))" +
            "       or lower(c.user.firstName) like lower(concat('%', :searchText,'%'))"+
            "       or lower(c.project.description) like lower(concat('%', :searchText,'%'))"+
            "       or lower(c.project.name) like lower(concat('%', :searchText,'%'))")
    Page<CaptainReport> search(String searchText, Pageable pageable);
}
