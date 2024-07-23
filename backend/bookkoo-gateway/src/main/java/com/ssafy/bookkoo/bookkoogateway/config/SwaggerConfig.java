package com.ssafy.bookkoo.bookkoogateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi bookServiceApi() {
        return GroupedOpenApi.builder()
                             .group("book-service")
                             .pathsToMatch("/book-service/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi curationServiceApi() {
        return GroupedOpenApi.builder()
                             .group("curation-service")
                             .pathsToMatch("/curation-service/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi libraryServiceApi() {
        return GroupedOpenApi.builder()
                             .group("library-service")
                             .pathsToMatch("/library-service/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi memberServiceApi() {
        return GroupedOpenApi.builder()
                             .group("member-service")
                             .pathsToMatch("/member-service/**")
                             .build();
    }
}