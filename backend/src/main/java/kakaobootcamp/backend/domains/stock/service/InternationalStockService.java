package kakaobootcamp.backend.domains.stock.service;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.common.util.webClient.WebClientUtil;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.service.KisAccessTokenService;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.InternationalStockDTO.InternationalOrderStockRequest;
import kakaobootcamp.backend.domains.stock.dto.InternationalStockDTO.OrderStockResponse;
import kakaobootcamp.backend.domains.stock.dto.KisBaseResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternationalStockService {

	private final KisProperties kisProperties;
	private final KisAccessTokenService kisAccessTokenService;
	private final MemberService memberService;
	private final WebClientUtil webClientUtil;

	// 헤더 설정
	private Map<String, String> makeHeaders(Member member, String trId) {
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId())
			.orElseThrow(() -> ApiException.from(KIS_ACCESS_TOKEN_NOT_FOUND));
		String accessToken = kisAccessToken.getAccessToken();

		Map<String, String> headers = new HashMap<>();
		headers.put("authorization", accessToken);
		headers.put("appkey", memberService.getDecryptedAppKey(member));
		headers.put("appsecret", memberService.getDecryptedSecretKey(member));
		headers.put("tr_id", trId);

		return headers;
	}

	// 응답 확인
	private void checkResponse(KisBaseResponse response) {
		if (!response.getRt_cd().equals("0")) {
			throw ApiException.of(HttpStatus.BAD_REQUEST, response.getMsg1());
		}
	}

	// TrId 조회
	private String getTrId(boolean isBuy) {
		if (isBuy) {
			return kisProperties.getInternational().getBuyTrId();
		} else {
			return kisProperties.getInternational().getSellTrId();
		}
	}

	// 주식 사기
	public void orderStock(Member member, InternationalOrderStockRequest request, boolean isBuy) {
		String uri = "/uapi/overseas-stock/v1/trading/order";
		String trId = getTrId(isBuy);

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, trId);

		// 본문 설정
		OrderStockResponse response = webClientUtil.postFromKis(
			headers,
			uri,
			request,
			OrderStockResponse.class);

		checkResponse(response);
	}
}
