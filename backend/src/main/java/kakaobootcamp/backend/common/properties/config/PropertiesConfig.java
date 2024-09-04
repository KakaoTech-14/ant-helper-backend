package kakaobootcamp.backend.common.properties.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import kakaobootcamp.backend.common.properties.AiServerProperties;
import kakaobootcamp.backend.common.properties.CorsProperties;
import kakaobootcamp.backend.common.properties.EmailProperties;
import kakaobootcamp.backend.common.properties.JwtProperties;
import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.common.properties.PublicDataPortalProperties;
import kakaobootcamp.backend.common.properties.RedisProperties;
import kakaobootcamp.backend.common.properties.SecurityProperties;

@Configuration
@EnableConfigurationProperties(value = {
	KisProperties.class,
	RedisProperties.class,
	SecurityProperties.class,
	JwtProperties.class,
	CorsProperties.class,
	EmailProperties.class,
	AiServerProperties.class,
	PublicDataPortalProperties.class
})
public class PropertiesConfig {
}

