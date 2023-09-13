package com.example.springbootkeyclock.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration

        //provide definition
@OpenAPIDefinition(
        info = @Info(
                description = "OPEN API Configuration with Spring Security",
                title = "OPEN API",
                version = "1.0"
                //We can add license, termsOfService and contact in info section
        ),

        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8082"
                )

        }
)
@SecurityScheme(
        name = "bearerToken",
        description = "JWT token based Auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        //we give out token to header
        in = SecuritySchemeIn.HEADER
)

public class OpenAPI {
}
