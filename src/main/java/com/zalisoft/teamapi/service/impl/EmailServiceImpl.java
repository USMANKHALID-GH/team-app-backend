package com.zalisoft.teamapi.service.impl;

import com.zalisoft.teamapi.model.Project;
import com.zalisoft.teamapi.service.EmailService;
import com.zalisoft.teamapi.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl  implements EmailService {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessageToProjectManagersForUnfinishedProject() {
        List<Project> projectList=projectService.findUnfinishedProject();
        log.info("inside email----");
        projectList.stream()
                .forEach(m-> {
                    try {sendMail("WARNING PROJECT ID: " +
                            ""+m.getId()+ "  SON Tarih Gecti: " +
                            ""+m.getDeadline() ,m.getProjectManager().getEmail());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    private void sendMail(String message ,String receiver) throws MessagingException, UnsupportedEncodingException {
        log.info("mesage : {}   receiver : {}",message,receiver);
        MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage());
        helper.setText("WARNING");
        helper.setSubject("WARNING");
        helper.setTo(receiver);
        helper.setFrom("loftyusman@gmail.com", "ZALISOFT");
        helper.setText(message, true);
        mailSender.send(helper.getMimeMessage());
    }
}
