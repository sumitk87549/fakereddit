
package com.fakereddit.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration{

    @Bean
    public OpenAPI fakeredditOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("FakeReddit API")
                        .description("A clone of reddit application")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().url("https://t.me/IamSumit0").name("Sumit Kumar").email("sumitk87549@gmail.com")))
                .externalDocs(new ExternalDocumentation());
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("fakereddit")
                .pathsToMatch("/**")
                .build();
    }
}
