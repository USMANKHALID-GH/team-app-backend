package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.DailyReportDto;
import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.mapper.ReportDtoMapper;
import com.zalisoft.teamapi.model.*;
import com.zalisoft.teamapi.repository.DailyReportRepository;
import com.zalisoft.teamapi.service.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DailyReportServiceImpl implements DailyReportService {


    @Autowired
    private UserService userService;
    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDtoMapper reportDtoMapper;

    @Autowired
    private ParameterService parameterService;

    @Override
    public List<DailyReport> search(Pageable pageable, String search) {
        return dailyReportRepository.search(search,pageable).getContent();
    }

    @Override
    public List<DailyReport> findReportByCurrentUser() {
        User user =userService.findCurrentUser();
        return dailyReportRepository.findByCurrentUser(user.getId());
    }

    @Override
    public DailyReport findById(long id) {
        return dailyReportRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_REPORT_MSG_001));
    }

    @Override
    public List<DailyReport> findReportByCaptains(int days) {
        User user=userService.findCurrentUser();
        LocalDateTime localDate=LocalDateTime.now();
        LocalDateTime desiredDateTime = localDate.minus(days, ChronoUnit.DAYS);
        return dailyReportRepository.findReportByCaptain(user.getId(),desiredDateTime);
    }

    @Override
    public List<DailyReport> findIncompleteReport() {
        return dailyReportRepository.findReportsByCompletedFalse();
    }

    @Override
    public List<User> findUsersUnsentReport(String tc) {
        List<User> usersUnsentReports=userService.findUserUnsentReportByCaptainTc(tc);
        List<User>  usersOnDayOff= findUsersOnDayOff();
        usersUnsentReports.removeAll(usersOnDayOff);
        return usersUnsentReports;
    }

    @Override
    public DailyReport save(DailyReportDto dailyReportDto) {
        User user =userService.findCurrentUser();
        if(!dailyReportRepository.existsByCreatedDate(LocalDate.now(),user.getId())){
        DailyReport dailyReport =new DailyReport();
        if(ObjectUtils.isEmpty(Integer.valueOf(dailyReportDto.getMinutes()))){
            throw new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_002);
        }

        if(!CollectionUtils.isEmpty(dailyReportDto.getReports())){
            List<Report> reports=dailyReportDto.getReports()
                   .stream()
                   .map(s-> {
                      reportService.validateReportToBesave(s);
                       return reportDtoMapper.toEntity(s);
                   })
                   .collect(Collectors.toList());

             dailyReport.setReports(reports);
        }


        dailyReport.setMinutes(dailyReportDto.getMinutes());
        dailyReport.setUser(user);
        dailyReport.setCompleted(checkIfIsMoreThan8hours(dailyReport.getMinutes()));
        return dailyReportRepository.save(dailyReport);
        }

        else
            throw  new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_005);
    }


    @Override
    public void delete(long id) {
        dailyReportRepository.delete(findById(id));
    }



    @Override
    public void update(DailyReportDto dailyReportDto, long id) {
        User user = userService.findCurrentUser();
        DailyReport dailyReport = findById(id);

        if (user.getId().equals(dailyReport.getUser().getId())) {
            if (!ObjectUtils.isEmpty(dailyReportDto.getMinutes())) {
                dailyReport.setMinutes(dailyReportDto.getMinutes());
                dailyReport.setCompleted(checkIfIsMoreThan8hours(dailyReport.getMinutes()));
            }
            List<ReportDto> reportDtos = dailyReportDto.getReports();

            if (!CollectionUtils.isEmpty(dailyReportDto.getReports())) {
                List<Report> reports = new ArrayList<>();
                for (ReportDto reportDto : reportDtos) {
                    reports.add(reportDtoMapper.toEntity(reportService.validateReportForUpdate(reportDto)));
                }
                dailyReport.setReports(reports);
            }

            dailyReportRepository.save(dailyReport);
            return;
        }

        throw new BusinessException(ResponseMessageEnum.BACK_CURRENT_USER_MSG_001);
    }


    @Override
    public void setDayOff() {
        User user=userService.findCurrentUser();
        if(!dailyReportRepository. checkIfOnDayOff(user.getId()).isPresent()){
        DailyReport dailyReport =new DailyReport();
        dailyReport.setUser(user);
        dailyReport.setDayOff(true);
        dailyReportRepository.save(dailyReport);}
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_004);
    }

    @Override
    public void uncheckAllDayOff() {
      dailyReportRepository.updateIsDayOffToFalse();

    }

    @Override
    public List<User> findUsersOnDayOff() {
        return dailyReportRepository.findAllUserOnDayOff();
    }


    private boolean checkIfIsMoreThan8hours(int mins){
        LocalTime inputTime = LocalTime.of(mins / 60, mins % 60);
        return inputTime.isAfter(LocalTime.of(parameterService.getDailyReportLimit(), 0));
    }


}
