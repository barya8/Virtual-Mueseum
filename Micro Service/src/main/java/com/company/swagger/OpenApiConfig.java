package com.company.swagger;

import com.company.model.ServiceResult;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI MuseumMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Museum API's")
                        .description(("This API's connecting between the three " +
                                "components front, back and db"))
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .path("/api/auth/resetPassword", new io.swagger.v3.oas.models.PathItem()
                    .post(new io.swagger.v3.oas.models.Operation()
                            .responses(new io.swagger.v3.oas.models.responses.ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("OK").content(
                                            new Content().addMediaType("application/json",
                                                    new MediaType().schema(
                                                            new Schema<ServiceResult>()
                                                                    .$ref("#/components/schemas/ServiceResult")))
                                                    )
                                            )
                                    .addApiResponse("500", new ApiResponse().description("Internal Server Error").content(
                                            new Content().addMediaType("application/json",
                                                    new MediaType().schema(
                                                            new Schema<ServiceResult>()
                                                                    .$ref("#/components/schemas/ServiceResult")))
                                            )
                                    )
                            )
                    ));
    }
}