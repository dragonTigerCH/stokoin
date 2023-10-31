package com.bsc.stokoin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("stokoin-public")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("stokoin-admin")
                .pathsToMatch("/**")
//                .addOpenApiMethodFilter(method -> method.isAnnotationPresent() // Admin 어노테이션이 있는 메서드만 필터링
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(components())
                //Authorization 헤더
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("BSC-Stokoin API")
                .description("BSC-Stokoin API 명세서")
                .version("1.0.0");
    }

    private Components components() {
        return new Components().addSecuritySchemes("Authorization", new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP) // HTTP 방식
                .scheme("Bearer")
                .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional);
    }

}
