package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {

    List<Report> findAll(Pageable pageable , String search);

    List<Report>  findReportByCurrentUser();

    Report findById(long id);

    List<Report> findReportByCaptains(int days);

    List<Report> findIncompleteReport();

    List<User>    findUserUnsentReport();


}
