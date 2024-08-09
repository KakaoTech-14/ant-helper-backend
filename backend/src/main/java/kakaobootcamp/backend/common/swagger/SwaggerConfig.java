package kakaobootcamp.backend.common.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {

		// SecurityScheme securityScheme = new SecurityScheme()
		// 	.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
		// 	.in(SecurityScheme.In.HEADER).name("Authorization");
		// SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		// return new OpenAPI()
		// 	.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
		// 	.security(Arrays.asList(securityRequirement))
		// 	.info(apiInfo());
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("antHelper api 명세서")
			.description("antHelper api 명세서입니다.")
			.version("1.0.0");
	}
}
