package com.sanjati.core.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("timonin.a,koshelev.s,tananina.i,gavrilov.v,aushin.e,beliakov.s,antasuk.i - TaskServiceManager - Сервис позволяющий распределять задачи")
                                .version("1")
                );
    }
}
