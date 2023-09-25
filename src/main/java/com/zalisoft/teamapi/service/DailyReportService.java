package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.DailyReportDto;
import com.zalisoft.teamapi.model.DailyReport;
import com.zalisoft.teamapi.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DailyReportService {

    List<DailyReport> search(Pageable pageable , String search);

    List<DailyReport>  findReportByCurrentUser();

    DailyReport findById(long id);

    List<DailyReport> findReportByCaptains(int days);

    List<DailyReport> findIncompleteReport();

    List<User> findUsersUnsentReport(String tc);

    DailyReport save(DailyReportDto dailyReportDto, List<Long> pId, long uId, long tId);


    void delete(long id);

    void update(DailyReportDto dto, long id);

    void  setDayOff();

    void   uncheckAllDayOff();

    List<User> findUsersOnDayOff();




}
