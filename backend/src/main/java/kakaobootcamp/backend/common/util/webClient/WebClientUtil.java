package kakaobootcamp.backend.common.util.webClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO.KisErrorResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

	@Qualifier("kisWebClient")
	private final WebClient kisWebClient;

	@Qualifier("aiServerWebClient")
	private final WebClient aiServerWebClient;

	@Qualifier("publicDataPortalWebClient")
	private final WebClient publicDataPortalWebClient;

	public <T> T getFromKis(Map<String, String> headersMap, String url, MultiValueMap<String, String> params,
		Class<T> responseType) {
		return kisWebClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.path(url)
				.queryParams(params)
				.build())
			.headers(headers -> headersMap.forEach(headers::set))
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}

	public <T, V> T postFromKis(Map<String, String> headersMap, String url, V request, Class<T> responseType) {
		return kisWebClient
			.post()
			.uri(url)
			.headers(headers -> headersMap.forEach(headers::set))
			.bodyValue(request)
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}

	public <T> T getFromAiServer(Map<String, String> headersMap, String url, MultiValueMap<String, String> params,
		Class<T> responseType) {
		return aiServerWebClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.path(url)
				.queryParams(params)
				.build())
			.headers(headers -> headersMap.forEach(headers::set))
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}

	public <T, V> T postFromAiServer(Map<String, String> headersMap, String url, V request, Class<T> responseType) {
		return aiServerWebClient
			.post()
			.uri(url)
			.headers(headers -> headersMap.forEach(headers::set))
			.bodyValue(request)
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				response -> response.bodyToMono(KisErrorResponse.class)
					.flatMap(error -> Mono.error(ApiException.of(
						(HttpStatus)response.statusCode(),
						error.getMsg1()
					)))
			)
			.bodyToMono(responseType)
			.block();
	}

	public <T> T getFromPublicDataPortal(Map<String, String> headersMap, String url,
		MultiValueMap<String, String> params, Class<T> responseType) {
		return publicDataPortalWebClient
			.get()
			.uri(uriBuilder -> uriBuilder
				.path(url)
				.queryParams(params)
				.build())
			.headers(headers -> headersMap.forEach(headers::set))
			.retrieve()
			.onStatus(
				HttpStatusCode::is4xxClientError,
				clientResponse -> Mono.error(ApiException.from(ErrorCode.BAD_REQUEST))
			)
			.onStatus(
				HttpStatusCode::is5xxServerError,
				clientResponse -> Mono.error(ApiException.from(ErrorCode.INTERNAL_SERVER_ERROR))
			)
			.bodyToMono(responseType)
			.block();
	}
}
