package com.zalisoft.teamapi.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMessageToProjectManagersForUnfinishedProject();

    void countUsersWithMoreThanTheGivenOccurrences() throws MessagingException, UnsupportedEncodingException;
}
