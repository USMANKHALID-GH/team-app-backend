package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.dto.ReportDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.mapper.ReportDtoMapper;
import com.zalisoft.teamapi.model.CaptainReport;
import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.model.Report;
import com.zalisoft.teamapi.model.User;
import com.zalisoft.teamapi.repository.CaptainReportRepository;
import com.zalisoft.teamapi.service.CaptainReportService;
import com.zalisoft.teamapi.service.ProjectService;
import com.zalisoft.teamapi.service.ReportService;
import com.zalisoft.teamapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CaptainReportServiceImpl implements CaptainReportService {

    @Autowired
    private CaptainReportRepository captainReportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReportDtoMapper reportDtoMapper;

    @Autowired
    private ReportService reportService;



    @Override
    public CaptainReport save(CaptainReportDto captainReportDto) {
        User user=userService.findCurrentUser();
        if(!captainReportRepository.existsByCreatedDate(LocalDate.now(),user.getId())) {
            CaptainReport captainReport = new CaptainReport();
            if (!CollectionUtils.isEmpty(captainReportDto.getReports())) {
                List<Report> reports = captainReportDto.getReports()
                        .stream().map(s -> {
                            reportService.validateReportToBesave(s);
                            checkIfUserIsCaptainOrProjectManager(s.getProjectId());
                            return reportDtoMapper.toEntity(s);
                        }).collect(Collectors.toList());

                captainReport.setReports(reports);
            }

            captainReport.setUser(user);
            captainReport.setMinutes(captainReportDto.getMinutes());
            return captainReportRepository.save(captainReport);
        }
        throw new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_001);


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
        User user=userService.findCurrentUser();
        if (user.getId().equals(captainReport.getUser().getId())) {

             List<ReportDto> reportDtos=captainReportDto.getReports();

            if (!CollectionUtils.isEmpty(captainReportDto.getReports())) {
                List<Report> reports = new ArrayList<>();
                for (ReportDto reportDto : reportDtos) {
                    reports.add(reportDtoMapper.toEntity(reportService.validateReportForUpdate(reportDto)));
                }
                captainReport.setReports(reports);
            }
            if(!ObjectUtils.isEmpty(captainReportDto.getMinutes())){
              captainReport.setMinutes(captainReportDto.getMinutes());
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
        LocalDateTime endingDate = ending.atStartOfDay();
        LocalDateTime dateNow = ending.atStartOfDay();
        return ChronoUnit.HOURS.between(endingDate, dateNow);

    }
    private void checkIfUserIsCaptainOrProjectManager(long id){
        User user=userService.findCurrentUser();
        if(!(userService.checkIfCaptain(user.getId()) || user.equals(projectService.findById(id).getProjectManager()))){
            throw  new BusinessException(ResponseMessageEnum.BACK_CAPTAIN_REPORT_MSG_003);
        }
    }
}
