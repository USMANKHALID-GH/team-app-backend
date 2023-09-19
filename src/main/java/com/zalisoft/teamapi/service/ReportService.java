package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {

    List<Report> search(Pageable pageable , String search);

    List<Report>  findReportByCurrentUser();

    Report findById(long id);

    List<Report> findReportByCaptains(int days);

    List<Report> findIncompleteReport();

    List<User>    findUserUnsentReport(String tc);

    Report   save(ReportDto reportDto , List<Long> pId, long uId, long tId);


    void delete(long id);

    void update(ReportDto dto,long id);


}
