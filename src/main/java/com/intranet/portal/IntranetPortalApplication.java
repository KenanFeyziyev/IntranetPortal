package com.intranet.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IntranetPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntranetPortalApplication.class, args);
    }

}
