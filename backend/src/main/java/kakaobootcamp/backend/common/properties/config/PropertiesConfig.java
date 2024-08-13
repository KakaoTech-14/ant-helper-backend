package kakaobootcamp.backend.common.properties.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.common.properties.RedisProperties;

@Configuration
@EnableConfigurationProperties(value = {
	KisProperties.class,
	RedisProperties.class
})
public class PropertiesConfig {
}

