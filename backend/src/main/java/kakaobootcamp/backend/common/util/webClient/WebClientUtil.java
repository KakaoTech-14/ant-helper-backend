package kakaobootcamp.backend.common.util.webClient;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO.KisErrorResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

	private final WebClient webClient;

	public <T> T get(Map<String, String> headersMap, String url, Class<T> responseType) {
		return webClient
			.get()
			.uri(url)
			.headers(headers -> headersMap.forEach(headers::set))
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(CustomException.from(
						(HttpStatus) response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(CustomException.from(
						(HttpStatus) response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}

	public <T,V> T post(Map<String, String> headersMap, String url, V request, Class<T> responseType) {
		return webClient
			.post()
			.uri(url)
			.headers(headers -> headersMap.forEach(headers::set))
			.body(Mono.just(request), request.getClass())
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(CustomException.from(
						(HttpStatus) response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(CustomException.from(
						(HttpStatus) response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}
}
