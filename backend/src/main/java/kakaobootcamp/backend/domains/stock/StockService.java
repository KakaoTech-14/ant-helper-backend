package kakaobootcamp.backend.domains.stock;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.common.util.webClient.WebClientUtil;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.service.KisAccessTokenService;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.KisOrderStockRequest;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockRequest;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

	private final KisProperties kisProperties;
	private final KisAccessTokenService kisAccessTokenService;
	private final MemberService memberService;
	private final WebClientUtil webClientUtil;

	public void orderStock(OrderStockRequest request, Member member) {
		String uri = "/uapi/domestic-stock/v1/trading/order-cash";

		// accessToken 가져오기
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId()).
			orElseThrow(() -> ApiException.from(KIS_ACCESS_TOKEN_NOT_FOUND));
		String accessToken = kisAccessToken.getAccessToken();

		// 헤더 설정
		Map<String, String> headers = new HashMap<>();
		headers.put("authorization", accessToken);
		headers.put("appkey", memberService.getDecryptedAppKey(member));
		headers.put("appsecret", memberService.getDecryptedSecretKey(member));
		headers.put("tr_id", kisProperties.getOrderTrId());

		// 본문 설정
		KisOrderStockRequest kisOrderStockRequest = makeKisOrderStockRequestFromOrderStockRequestAndMember(request,
			member);

		OrderStockResponse response = webClientUtil.post(headers, uri, kisOrderStockRequest, OrderStockResponse.class);

		checkOrderAndSellStockResponse(response);
	}

	public void sellStock(OrderStockRequest request, Member member) {
		String uri = "/uapi/domestic-stock/v1/trading/order-cash";

		// accessToken 가져오기
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId()).
			orElseThrow(() -> ApiException.from(KIS_ACCESS_TOKEN_NOT_FOUND));
		String accessToken = kisAccessToken.getAccessToken();

		// 헤더 설정
		Map<String, String> headers = new HashMap<>();
		headers.put("authorization", accessToken);
		headers.put("appkey", memberService.getDecryptedAppKey(member));
		headers.put("appsecret", memberService.getDecryptedSecretKey(member));
		headers.put("tr_id", kisProperties.getSellTrId());

		// 본문 설정
		KisOrderStockRequest kisOrderStockRequest = makeKisOrderStockRequestFromOrderStockRequestAndMember(request,
			member);

		OrderStockResponse response = webClientUtil.post(headers, uri, kisOrderStockRequest, OrderStockResponse.class);

		checkOrderAndSellStockResponse(response);
	}

	private void checkOrderAndSellStockResponse(OrderStockResponse response) {
		if (!response.getRt_cd().equals("0")) {
			throw CustomException.from(HttpStatus.BAD_REQUEST, response.getMsg1());
		}

	}

	private KisOrderStockRequest makeKisOrderStockRequestFromOrderStockRequestAndMember(OrderStockRequest request,
		Member member) {
		return KisOrderStockRequest.builder()
			.CANO(member.getComprehensiveAccountNumber())
			.ACNT_PRDT_CD(member.getAccountProductCode())
			.PDNO(request.getPDNO())
			.ORD_DVSN(request.getORD_DVSN())
			.ORD_QTY(request.getORD_QTY())
			.ORD_UNPR(request.getORD_UNPR())
			.build();
	}
}
