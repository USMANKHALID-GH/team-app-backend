package com.zalisoft.teamapi.task;

import com.zalisoft.teamapi.constant.CronConstant;
import com.zalisoft.teamapi.service.EmailService;
import com.zalisoft.teamapi.service.DailyReportService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class ReportJobs {


    @Autowired
    private EmailService emailService;

    @Autowired
    private DailyReportService reportService;



    @Scheduled(cron = CronConstant.EVERY_MINUTE)
    @SchedulerLock(name = "sendCautionToUnsentReport")
    private  void sendCautionToUnsentReport() throws MessagingException, UnsupportedEncodingException {
        log.info("inside: {}","sendCautionToUnsentReport");
        emailService.countUsersWithMoreThanTheGivenOccurrences();
    }


    @Scheduled(cron = CronConstant.EVERY_MONDAY)
    @SchedulerLock(name = "startNewDay")
    private  void  startNewDay(){
        log.info("setting dayoff to false");
        reportService.uncheckAllDayOff();
    }
}
