package org.hyperoptic.infrastructure.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Hyperoptic")
                        .description("Home project")
                        .version("v1.0")
                        .contact(
                                new Contact()
                                        .name("Nikola Rusimovic")
                                        .email("nikola.rusimovic@gmail.com")
                        )
                        .termsOfService("TOS")

                );
    }
}
