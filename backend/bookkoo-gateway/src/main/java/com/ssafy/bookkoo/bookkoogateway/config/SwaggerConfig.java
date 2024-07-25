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
                             .pathsToMatch("/books/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi curationServiceApi() {
        return GroupedOpenApi.builder()
                             .group("curation-service")
                             .pathsToMatch("/curations/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi libraryServiceApi() {
        return GroupedOpenApi.builder()
                             .group("library-service")
                             .pathsToMatch("/libraries/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi memberServiceApi() {
        return GroupedOpenApi.builder()
                             .group("member-service")
                             .pathsToMatch("/members/**")
                             .build();
    }
}