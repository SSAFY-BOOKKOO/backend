package com.ssafy.bookkoo.memberservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class GmailSendService implements MailSendService {

    private final JavaMailSender emailSender;
    @Override
    public boolean sendMail(String subject, String content, List<String> receivers)
        throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setText(content);
        helper.setTo(receivers.toArray(new String[0]));
        emailSender.send(message);
        return false;
    }
}
