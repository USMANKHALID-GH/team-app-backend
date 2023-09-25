package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.DailyReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.DailyReport;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.Team;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.DailyReportRepository;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.DailyReportService;
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
public class DailyReportServiceImpl implements DailyReportService {


    @Autowired
    private UserService userService;
    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamService teamService;

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
        List<User> usersUnsentReports=userService.findUserUnsentReport(tc);
        List<User>  usersOnDayOff= findUsersOnDayOff();
        usersUnsentReports.removeAll(usersOnDayOff);
        return usersUnsentReports;
    }

    @Override
    public DailyReport save(DailyReportDto dailyReportDto, List<Long> pId, long uId, long tId) {
        DailyReport dailyReport =new DailyReport();
        List<Project> project;
        if(!CollectionUtils.isEmpty(pId)){
            project=projectService.findByMultipleId(pId);
            dailyReport.setProject(project);
        }

        if(ObjectUtils.isEmpty(Integer.valueOf(dailyReportDto.getHours()))){
            throw new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_002);
        }

        if(StringUtils.isEmpty(dailyReportDto.getDetails())){
            throw new BusinessException(ResponseMessageEnum.BACK_REPORT_MSG_003);
        }


        User user=userService.findById(uId);

        Team team=teamService.findById(tId);
        dailyReport.setHours(dailyReportDto.getHours());
        dailyReport.setMinutes(dailyReportDto.getMinutes());
        dailyReport.setPersonLearning(dailyReportDto.getPersonLearning());
        dailyReport.setDetails(dailyReportDto.getDetails());
        dailyReport.setUser(user);
        dailyReport.setTeam(team);
        dailyReport.setCompleted(checkIfIsMoreThan8hours(dailyReport.getHours(), dailyReport.getMinutes()));

        return dailyReportRepository.save(dailyReport);
    }


    @Override
    public void delete(long id) {
        dailyReportRepository.delete(findById(id));
    }

    @Override
    public void update(DailyReportDto dto, long id) {
        User user=userService.findCurrentUser();
        DailyReport dailyReport =findById(id);
        List<Project> project;
        if(user.getId().equals(dailyReport.getUser().getId())){
            throw   new BusinessException(ResponseMessageEnum.BACK_CURRENT_USER_MSG_001);
        }

        if(!CollectionUtils.isEmpty(dto.getProject())){
         List<Long> ids=dto.getProject().stream().map(s->s.getId()).collect(Collectors.toList());
            project=projectService.findByMultipleId(ids);
            dailyReport.setProject(project);
        }

        if(!ObjectUtils.isEmpty(Integer.valueOf(dto.getHours()))){
           dailyReport.setHours(dto.getHours());
        }

        if(!ObjectUtils.isEmpty(Integer.valueOf(dto.getMinutes()))){
            dailyReport.setHours(dto.getMinutes());
        }

        if(!StringUtils.isEmpty(dto.getDetails())){
          dailyReport.setDetails(dto.getDetails());
        }

        if(!StringUtils.isEmpty(dto.getPersonLearning())){
            dailyReport.setPersonLearning(dto.getPersonLearning());
        }

        dailyReport.setCompleted(checkIfIsMoreThan8hours(dailyReport.getHours(), dailyReport.getMinutes()));



        dailyReportRepository.save(dailyReport);
    }

    @Override
    public void setDayOff() {
        User user=userService.findCurrentUser();
        if(!dailyReportRepository. checkIfOnDayOff(user.getId()).isPresent()){
        DailyReport dailyReport =new DailyReport();
        dailyReport.setUser(user);
        dailyReport.setDayOff(true);
        dailyReport.setCompleted(true);
        dailyReport.setHours(8);
        dailyReport.setPersonLearning("day off");
        dailyReport.setDetails("day off");
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


    private boolean checkIfIsMoreThan8hours(int hours,int mins){
        LocalTime inputTime = LocalTime.of(hours, mins);
        return inputTime.isAfter(LocalTime.of(8, 0));
    }
}
