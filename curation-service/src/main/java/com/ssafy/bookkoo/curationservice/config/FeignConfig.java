package com.ssafy.bookkoo.curationservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ssafy.bookkoo.curationservice")
public class FeignConfig {

}
