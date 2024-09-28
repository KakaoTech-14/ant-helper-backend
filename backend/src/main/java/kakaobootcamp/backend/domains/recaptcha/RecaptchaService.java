package kakaobootcamp.backend.domains.recaptcha;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.properties.RecaptchaProperties;
import kakaobootcamp.backend.domains.recaptcha.dto.RecaptchaDTO.ValidateRecaptchaRequest;
import kakaobootcamp.backend.domains.recaptcha.dto.RecaptchaDTO.ValidateRecaptchaResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RecaptchaService {

	@Qualifier("recaptchaWebClient")
	private final WebClient recaptchaWebClient;

	private final RecaptchaProperties recaptchaProperties;

	public ValidateRecaptchaResponse validateRecaptcha(ValidateRecaptchaRequest request) {
		final String token = request.getToken();

		return recaptchaWebClient
			.post()
			.uri(
				uriBuilder -> uriBuilder
					.queryParam("secret", recaptchaProperties.getSecret())
					.queryParam("response", token)
					.build())
			.retrieve()
			.onStatus(
				status -> status.is4xxClientError() || status.is5xxServerError(),
				response -> response.bodyToMono(String.class)
					.flatMap(error -> Mono.error(ApiException.from(CAPTCHA_SERVER_ERROR))))
			.bodyToMono(ValidateRecaptchaResponse.class)
			.block();
	}
}
