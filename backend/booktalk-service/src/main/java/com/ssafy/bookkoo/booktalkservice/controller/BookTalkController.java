package com.ssafy.bookkoo.booktalkservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booktalks/api")
public class BookTalkController {

    @GetMapping
    public String getBookTalk() {
        return "booktalk-service";
    }
}
