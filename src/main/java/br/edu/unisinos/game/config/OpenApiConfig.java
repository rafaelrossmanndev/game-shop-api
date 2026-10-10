package br.edu.unisinos.game.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gameShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Game Shop API")
                        .description("API para gerenciamento de jogadores, itens, inventário e compras da Game Shop.")
                        .version("v1")
                        .contact(new Contact().name("Game Shop")));
    }
}
