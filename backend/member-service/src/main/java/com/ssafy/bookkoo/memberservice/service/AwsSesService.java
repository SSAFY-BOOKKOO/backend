package com.ssafy.bookkoo.memberservice.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.ssafy.bookkoo.memberservice.dto.AwsSesDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSesService implements MailSendService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public boolean sendMail(String subject, String content, List<String> receivers) {
        try {
            final AwsSesDto awsSesDto = AwsSesDto.builder()
                                                 .to(receivers)
                                                 .subject(subject)
                                                 .content(content)
                                                 .build();

            final SendEmailResult sendEmailResult = amazonSimpleEmailService
                .sendEmail(awsSesDto.toSendRequestDto());

            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("Email Failed");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

}