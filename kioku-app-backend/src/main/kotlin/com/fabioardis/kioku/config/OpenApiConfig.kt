package com.fabioardis.kioku.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .openapi("3.0.1")
            .info(
                Info()
                    .title("Kioku API")
                    .version("0.0.1")
                    .description("API that manages cards and decks")
                    .contact(
                        Contact()
                            .name("Fabio Ardis")
                            .email("fabioardis@gmail.com")
                    )
            )
    }
}