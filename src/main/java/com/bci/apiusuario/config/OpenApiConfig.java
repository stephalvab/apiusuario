package com.bci.apiusuario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title= "Api de Usuarios",
                version= "1.0.0",
                description= "Api para usuarios"
        )
)
public class OpenApiConfig {

}
