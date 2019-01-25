// Copyright (c) 2019 Travelex Ltd

package com.travelex.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdminApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringBootAdminApplication.class)
                        .web(WebApplicationType.REACTIVE)
                        .run(args);

    }
}