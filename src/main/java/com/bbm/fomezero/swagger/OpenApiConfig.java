package com.bbm.fomezero.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "FomeZero",
                description = "FomeZero is a full-featured food delivery platform built to connect local restaurants with " +
                        "customers across Mozambique." +
                        "The platform allows restaurants to register and manage their menus, while drivers can " +
                        "sign up to deliver orders quickly and reliably.\n",
                version = "1.0",
                contact = @Contact(
                        name = "Belmiro Mungoi",
                        email = "belmiromungoi@gmail.com",
                        url = "https://github.com/BelmiroMungoi"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "Bearer Authentication"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Faça o login na API, para poder usar perfeitamente a aplicação, " +
                "coloque o seu token de autenticação no campo abaixo e clique no botão Authorize.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
