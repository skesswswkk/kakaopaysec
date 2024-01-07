package com.example.stockranking.stock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "실시간 주식 순위 정보 서비스"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
}
