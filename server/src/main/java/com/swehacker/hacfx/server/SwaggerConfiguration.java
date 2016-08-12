package com.swehacker.hacfx.server;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(documentPaths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Home Automation Controller API Documentation")
                .description("Handles configuration and events from and to OpenHAB2")
                .contact(new Contact("Patrik Falk", "http://www.swehacker.com", "patrik@patrikfalk.com"))
                .termsOfServiceUrl("https://www.swehacker.com/hacfx")
                .license("MIT")
                .version("1.0")
                .build();
    }

    @SuppressWarnings("unchecked")
    private Predicate<String> documentPaths() {
        return Predicates.or(
                PathSelectors.regex("/rest.*"));
    }
}
