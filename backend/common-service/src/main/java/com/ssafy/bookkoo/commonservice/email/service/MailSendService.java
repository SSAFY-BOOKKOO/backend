package com.ssafy.bookkoo.commonservice.email.service;

import com.ssafy.bookkoo.commonservice.email.dto.request.RequestSendEmailDto;
import java.util.Arrays;

public interface MailSendService {

    String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    void sendMail(RequestSendEmailDto sendEmailDto);


    /**
     * 메일의 유효성 검증을 위한 default 메서드
     * @param emails
     * @return
     */
    default String[] validMailFilter(String[] emails) {
        return Arrays.stream(emails)
                     .filter((email) -> email.matches(EMAIL_REGEX))
                     .toList()
                     .toArray(new String[0]);
    }
}
