package com.ssafy.bookkoo.bookkoogateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    servers = {@Server(url = "/", description = "Default Server URL")
})
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

    @Bean
    public GroupedOpenApi notificationServiceApi() {
        return GroupedOpenApi.builder()
                             .group("notification-service")
                             .pathsToMatch("/notification/**")
                             .build();
    }
}