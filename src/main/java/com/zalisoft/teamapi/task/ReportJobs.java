package com.zalisoft.teamapi.task;

import com.zalisoft.teamapi.constant.CronConstant;
import com.zalisoft.teamapi.service.CautionService;
import com.zalisoft.teamapi.service.EmailService;
import com.zalisoft.teamapi.service.ParameterService;
import com.zalisoft.teamapi.service.ReportService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Component
@Slf4j
public class ReportJobs {


    @Autowired
    private EmailService emailService;



    @Scheduled(cron = CronConstant.EVERY_MINUTE)
    @SchedulerLock(name = "sendCautionToUnsentReport")
    private  void sendCautionToUnsentReport() throws MessagingException, UnsupportedEncodingException {
        log.info("inside: {}","sendCautionToUnsentReport");
        emailService.countUsersWithMoreThanTheGivenOccurrences();
    }


    @Scheduled(cron = CronConstant.EVERY_MINUTE)
    @SchedulerLock(name = "saveCaution")
    private  void  sendCautionToMessage(){
        log.info("something will be figured out");
    }
}
