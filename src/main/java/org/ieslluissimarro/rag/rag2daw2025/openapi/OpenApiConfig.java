package org.ieslluissimarro.rag.rag2daw2025.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Edgar",
                        email = "edgtorjor@alu.edu.gva.es",
                        url = "https://aules.edu.gva.es/fp/course/view.php?id=137282"
                ),
                description = "Documentación OpenApi para la API Rest 'Instituto' del módulo DWES",
                title = "Documentación API Rest 'Instituto'",
                version = "1.0",
                license = @License(
                        name = "Licencia",
                        url = "https://some-url.com"
                ),
                termsOfService = "https://ieslluissimarro.org/DWES/TermsOfService/"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8090"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://ieslluissimarro.org/DWES/Production_API/"
                )
        }
)
public class OpenApiConfig {
}
