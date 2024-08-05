package com.ssafy.bookkoo.booktalkservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ssafy.bookkoo.booktalkservice")
public class FeignConfig {

}
