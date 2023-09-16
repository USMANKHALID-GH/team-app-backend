package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.ReportRepository;
import com.zalisoft.teamapi.service.ReportService;
import com.zalisoft.teamapi.service.UserService;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    private UserService userService;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Report> findAll(Pageable pageable, String search) {
        return reportRepository.search(search,pageable).getContent();
    }

    @Override
    public List<Report> findReportByCurrentUser() {
        User user =userService.findCurrentUser();
        return reportRepository.findByCurrentUser(user.getId());
    }

    @Override
    public Report findById(long id) {
        return reportRepository.findById(id)
                .orElseThrow(()-> new BusinessException(""));
    }

    @Override
    public List<Report> findReportByCaptains(int days) {
        User user=userService.findCurrentUser();
        LocalDateTime localDate=LocalDateTime.now();
        LocalDateTime desiredDateTime = localDate.minus(days, ChronoUnit.DAYS);
        return reportRepository.findReportByCaptain(user.getId(),desiredDateTime);
    }

    @Override
    public List<Report> findIncompleteReport() {
        return reportRepository.findReportsByCompletedFalse();
    }

    @Override
    public List<User> findUserUnsentReport() {
        return reportRepository.findUserUnsentReport();
    }
}
