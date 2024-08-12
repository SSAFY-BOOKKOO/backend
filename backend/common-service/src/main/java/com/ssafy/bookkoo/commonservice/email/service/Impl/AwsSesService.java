package com.ssafy.bookkoo.commonservice.email.service.Impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.ssafy.bookkoo.commonservice.email.dto.AwsSesDto;
import com.ssafy.bookkoo.commonservice.email.dto.request.RequestSendEmailDto;
import com.ssafy.bookkoo.commonservice.email.exception.EmailSendFailException;
import com.ssafy.bookkoo.commonservice.email.service.MailSendService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSesService implements MailSendService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    /**
     * 메일 발송 서비스 메서드
     *
     * @param sendEmailDto@return
     */
    public void sendMail(RequestSendEmailDto sendEmailDto) {
        try {
            String[] receivers = validMailFilter(sendEmailDto.receivers());

            final AwsSesDto awsSesDto = AwsSesDto.builder()
                                                 .to(List.of(receivers))
                                                 .subject(sendEmailDto.subject())
                                                 .content(sendEmailDto.content())
                                                 .build();

            amazonSimpleEmailService.sendEmail(awsSesDto.toSendRequestDto());
        } catch (Exception e) {
            throw new EmailSendFailException();
        }
    }
}