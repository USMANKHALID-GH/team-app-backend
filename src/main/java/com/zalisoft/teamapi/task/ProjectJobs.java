package com.zalisoft.teamapi.task;

import com.zalisoft.teamapi.constant.CronConstant;
import com.zalisoft.teamapi.service.EmailService;
import com.zalisoft.teamapi.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectJobs {

    @Autowired
    private ProjectService service;
    @Autowired
    private EmailService emailService;

    @Scheduled(cron = CronConstant.EVERY_MINUTE)
    @SchedulerLock(name = "sendMessageToProjectManagersForUnfinishedProject")
    private   void sendMessageToProjectManagersForUnfinishedProject(){
        log.info("job is working !!!!!!!!!!!!!!!!");
        emailService.sendMessageToProjectManagersForUnfinishedProject();
    }
}
