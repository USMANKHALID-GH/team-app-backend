package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.model.CaptainReport;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.CaptainReportRepository;
import com.zalisoft.teamapi.service.CaptainReportService;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CaptainReportServiceImpl implements CaptainReportService {

    @Autowired
    private CaptainReportRepository captainReportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @Override
    public CaptainReport save(CaptainReportDto captainReportDto, long projectId) {
        User user=userService.findCurrentUser();
        Project project=projectService.findById(projectId);
        if(userService.checkIfCaptain(user.getId()) || user.equals(project.getProjectManager())){
            if(StringUtils.isEmpty(captainReportDto.getDescription())){
                throw  new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_001);
            }

            CaptainReport captainReport=new CaptainReport();

            captainReport.setDescription(captainReportDto.getDescription());
            captainReport.setUser(user);
            captainReport.setHours(captainReportDto.getHours());
            captainReport.setNumberOfDays(getDays(project.getDeadline(),LocalDate.now()));
            captainReport.setHours(getHours(project.getDeadline(),LocalDate.now()));
            return captainReportRepository.save(captainReport);

        }
        throw  new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_003);
    }

    @Override
    public CaptainReport findById(long id) {
       return captainReportRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_002));
    }

    @Override
    public void delete(long id) {
        User user=userService.findCurrentUser();
       CaptainReport captainReport= findById(id);
       if(captainReport.getUser().equals(user)){
           captainReportRepository.delete(captainReport);
       }

    }

    @Override
    public void update(long id, CaptainReportDto captainReportDto) {
        CaptainReport captainReport=findById(id);
         if(captainReport.getUser().equals(userService.findCurrentUser())){
             if(StringUtils.isNotEmpty(captainReportDto.getDescription())) {
                 captainReport.setDescription(captainReportDto.getDescription());
             }
             captainReportRepository.save(captainReport);
             return;
         }

         throw  new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_003);
    }

    @Override
    public Page<CaptainReport> search(Pageable pageable, String search) {
        return captainReportRepository.search(search ,pageable);
    }


    private Long  getDays(LocalDate ending, LocalDate now){
       return ChronoUnit.DAYS.between(ending, now);
   }

    private Long  getHours(LocalDate ending, LocalDate now){
        return ChronoUnit.HOURS.between(ending, now);
    }
}
