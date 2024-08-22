package kakaobootcamp.backend.domains.stock;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO;
import kakaobootcamp.backend.domains.broker.service.KisAccessTokenService;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

	private final WebClient webClient;
	private final KisProperties kisProperties;
	private final KisAccessTokenService kisAccessTokenService;
	private final MemberService memberService;

	public void orderStock(OrderStockRequest request, Member member) {

		// accessToken 가져오기
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId()).
			orElseThrow(() -> CustomException.from(KIS_ACCESS_TOKEN_NOT_FOUND));
		String accessToken = kisAccessToken.getAccessToken();

		log.info("accessToken: {}", accessToken);
		log.info("appKey: {}", memberService.getDecryptedAppKey(member));
		log.info("secretKey: {}", memberService.getDecryptedSecretKey(member));
		webClient
			.post()
			.uri("/uapi/domestic-stock/v1/trading/order-cash")
			.headers(header -> {
				header.set("authorization", accessToken);
				header.set("appkey", memberService.getDecryptedAppKey(member));
				header.set("appsecret", memberService.getDecryptedSecretKey(member));
				header.set("tr_id", kisProperties.getOrderTrId());
			})
			.body(Mono.just(request), OrderStockRequest.class)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomException.from(INVALID_KEY)))
			.bodyToMono(BrokerDTO.GetAccessKeyResponse.class)
			.block();
	}
}
