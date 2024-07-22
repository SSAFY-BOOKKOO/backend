package com.ssafy.bookkoo.memberservice.service;

import jakarta.mail.MessagingException;
import java.util.List;

public interface MailSendService {

    boolean sendMail(String subject, String content, List<String> receivers)
        throws Exception;

}
