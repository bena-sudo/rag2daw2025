package org.ieslluissimarro.rag.rag2daw2025.openapi;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RAG2DAW2025 - API de Gestión de Seguridad",
                version = "1.0",
                description = "API REST para la gestión de seguridad en el sistema RAG2DAW2025, incluyendo autenticación, roles, permisos y auditoría.",
                contact = @Contact(
                        name = "Equipo de Desarrollo RAG",
                        email = "soporte@rag2daw2025.com",
                        url = "https://github.com/bena-sudo/rag2daw2025"
                )
        ),
        security = @SecurityRequirement(name = "BearerAuth"),
        servers = {
                @Server(url = "http://localhost:8090", description = "Servidor Local para Desarrollo"),
                @Server(url = "https://api.rag2daw2025.com", description = "Servidor de Producción")
        }
)
@SecurityScheme(
        name = "BearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}

