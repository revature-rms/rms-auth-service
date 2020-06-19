package com.revature.rms.auth.config;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Docket api: provides the primary API configuration with sensible defaults and convenience methods for configuration
     * @return returns an instance of ApiSelectorBuilder, which provides a way to control the endpoints exposed by Swagger.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.revature.rms.auth.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }

    /**
     * apiEndPointsInfo provides description of the swagger documentation endpoint
     * @return ApiInfoBuilder: returns a title, description, contact, license, licenseURL, and version of the API.
     */
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("RMS Auth service REST API")
                .description("Employee service ")
                .contact(new Contact("", "www.revature.com", ""))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}