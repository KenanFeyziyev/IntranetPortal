package com.intranet.portal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI intranetPortalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Intranet Portal API")
                        .version("1.0")
                        .description("Employee Management System APIs"));
    }
}