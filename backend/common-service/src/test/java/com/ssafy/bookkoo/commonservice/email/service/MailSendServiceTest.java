package com.ssafy.bookkoo.commonservice.email.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailSendServiceTest {

    @Autowired
    MailSendService mailSendService;


    @Test
    public void mailValidTest() {

        String[] ex = new String[]{
            "ys4512558@test.com",
            "ys4512558",
        };
        String[] strings = mailSendService.validMailFilter(ex);
        for (String string : strings) {
            System.out.println(string);
        }
    }

}