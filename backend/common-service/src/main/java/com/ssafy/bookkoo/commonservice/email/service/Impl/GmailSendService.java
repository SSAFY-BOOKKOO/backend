package com.ssafy.bookkoo.commonservice.email.service.Impl;

import com.ssafy.bookkoo.commonservice.email.dto.request.RequestSendEmailDto;
import com.ssafy.bookkoo.commonservice.email.exception.EmailSendFailException;
import com.ssafy.bookkoo.commonservice.email.service.MailSendService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    /**
     * JavaMailSender를 통해 메일을 전송합니다.
     *
     * @param sendEmailDto@return
     * @throws MessagingException
     */
    @Override
    public void sendMail(RequestSendEmailDto sendEmailDto) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(sendEmailDto.subject());
            helper.setText(sendEmailDto.content());
            helper.setTo(sendEmailDto.receivers());
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendFailException();
        }
    }
}
