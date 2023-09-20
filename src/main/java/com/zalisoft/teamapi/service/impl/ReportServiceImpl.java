package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.ReportRepository;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.ReportService;
import com.zalisoft.teamapi.service.TeamService;
import com.zalisoft.teamapi.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {


    @Autowired
    private UserService userService;
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamService teamService;

    @Override
    public List<Report> search(Pageable pageable, String search) {
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
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum. BACK_REPORT_MSG_001));
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
    public List<User> findUserUnsentReport(String tc) {

        return  userService.findUserUnsentReport(tc);
    }

    @Override
    public Report save(ReportDto reportDto, List<Long> pId, long uId, long tId) {
        Report report=new Report();
        List<Project> project;
        if(!CollectionUtils.isEmpty(pId)){
            project=projectService.findByMultipleId(pId);
            report.setProject(project);
        }

        if(ObjectUtils.isEmpty(Integer.valueOf(reportDto.getHours()))){
            throw new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_002);
        }

        if(StringUtils.isEmpty(reportDto.getDetails())){
            throw new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_003);
        }


        User user=userService.findById(uId);

        Team team=teamService.findById(tId);
        report.setHours(reportDto.getHours());
        report.setMinutes(reportDto.getMinutes());
        report.setPersonLearning(reportDto.getPersonLearning());
        report.setDetails(reportDto.getDetails());
        report.setUser(user);
        report.setTeam(team);
        report.setCompleted(checkIfIsMoreThan8hours(report.getHours(), report.getMinutes()));

        return reportRepository.save(report);
    }


    @Override
    public void delete(long id) {
        reportRepository.delete(findById(id));
    }

    @Override
    public void update(ReportDto dto, long id) {
        User user=userService.findCurrentUser();
        Report report=findById(id);
        List<Project> project;
        if(user.getId().equals(report.getUser().getId())){
            throw   new BusinessException(ResponseMessageEnum.BACK_CURRENT_USER_MSG_001);
        }

        if(!CollectionUtils.isEmpty(dto.getProject())){
         List<Long> ids=dto.getProject().stream().map(s->s.getId()).collect(Collectors.toList());
            project=projectService.findByMultipleId(ids);
            report.setProject(project);
        }

        if(!ObjectUtils.isEmpty(Integer.valueOf(dto.getHours()))){
           report.setHours(dto.getHours());
        }

        if(!ObjectUtils.isEmpty(Integer.valueOf(dto.getMinutes()))){
            report.setHours(dto.getMinutes());
        }

        if(!StringUtils.isEmpty(dto.getDetails())){
          report.setDetails(dto.getDetails());
        }

        if(!StringUtils.isEmpty(dto.getPersonLearning())){
            report.setPersonLearning(dto.getPersonLearning());
        }

        report.setCompleted(checkIfIsMoreThan8hours(report.getHours(), report.getMinutes()));



        reportRepository.save(report);
    }

    @Override
    public void setDayOff(boolean dayOff) {
        User user=userService.findCurrentUser();
        if(!reportRepository.existsByDayOffTrueAndUser(user.getId()).isPresent()){
        Report report=new Report();
        report.setUser(user);
        report.setDayOff(dayOff);
        report.setCompleted(true);
        report.setHours(0);
        report.setPersonLearning("day off");
        report.setDetails("day off");
        reportRepository.save(report);}
        else
            throw  new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_004);
    }

    @Override
    public void seekPermission() {
        User user =userService.findCurrentUser();

    }


    private boolean checkIfIsMoreThan8hours(int hours,int mins){
        LocalTime inputTime = LocalTime.of(hours, mins);
        return inputTime.isAfter(LocalTime.of(8, 0));
    }
}
