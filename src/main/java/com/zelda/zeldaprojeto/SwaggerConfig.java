package com.zelda.zeldaprojeto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Zelda-Api", version = "1", description = "API desenvolvida para ajudar fãns da franquia 'Zelda' "))

public class SwaggerConfig {
}
