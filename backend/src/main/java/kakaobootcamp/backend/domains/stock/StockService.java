package kakaobootcamp.backend.domains.stock;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.properties.KisProperties;
import kakaobootcamp.backend.common.util.webClient.WebClientUtil;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.service.KisAccessTokenService;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceRealizedProfitAndLossResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockPriceResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.KisBaseResponse;
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

	// 헤더 설정
	private Map<String, String> makeHeaders(Member member, String trId) {
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId()).
			orElseThrow(() -> ApiException.from(KIS_ACCESS_TOKEN_NOT_FOUND));
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
			throw CustomException.from(HttpStatus.BAD_REQUEST, response.getMsg1());
		}

	}

	public void orderStock(OrderStockRequest request, Member member) {
		String uri = "/uapi/domestic-stock/v1/trading/order-cash";

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, kisProperties.getOrderTrId());

		// 본문 설정
		KisOrderStockRequest kisOrderStockRequest = makeKisOrderStockRequestFromOrderStockRequestAndMember(request,
			member);

		OrderStockResponse response = webClientUtil.postFromKis(headers, uri, kisOrderStockRequest, OrderStockResponse.class);

		checkResponse(response);
	}

	public void sellStock(OrderStockRequest request, Member member) {
		String uri = "/uapi/domestic-stock/v1/trading/order-cash";

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, kisProperties.getSellTrId());

		// 본문 설정
		KisOrderStockRequest kisOrderStockRequest = makeKisOrderStockRequestFromOrderStockRequestAndMember(request,
			member);

		OrderStockResponse response = webClientUtil.postFromKis(headers, uri, kisOrderStockRequest, OrderStockResponse.class);

		checkResponse(response);
	}

	// KisOrderStockRequest를 OrderStockRequest와 Member로 만들어주는 메서드
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

	// 주식 잔고 조회
	public GetStockBalanceResponse getStockBalance(Member member, String fk, String nk) {
		String uri = "/uapi/domestic-stock/v1/trading/inquire-balance";

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, kisProperties.getGetBalanceTrId());

		// 파라미터 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("CANO", member.getComprehensiveAccountNumber()); // 종합계좌번호
		params.add("ACNT_PRDT_CD", member.getAccountProductCode()); // 계좌상품코드
		params.add("AFHR_FLPR_YN", "N"); // 시간외단일가 여부 (Y: 포함, N: 포함하지 않음)
		params.add("OFL_YN", ""); // 오프라인 여부 (기본값: 공란)
		params.add("INQR_DVSN", "02"); // 조회 구분 (01: 대출일별, 02: 종목별)
		params.add("UNPR_DVSN", "01"); // 단가 구분 (01: 기본값)
		params.add("FUND_STTL_ICLD_YN", "N"); // 펀드 결제분 포함 여부 (Y: 포함, N: 포함하지 않음)
		params.add("FNCG_AMT_AUTO_RDPT_YN", "N"); // 융자 금액 자동 상환 여부 (기본값: N)
		params.add("PRCS_DVSN", "00"); // 처리 구분 (00: 전일 매매 포함, 01: 전일 매매 미포함)
		params.add("CTX_AREA_FK100", fk); // 연속 조회 검색 조건 100 (최초 조회 시 공란)
		params.add("CTX_AREA_NK100", nk); // 연속 조회 키 100 (최초 조회 시 공란)

		GetStockBalanceResponse response = webClientUtil.getFromKis(headers, uri, params, GetStockBalanceResponse.class);

		checkResponse(response);

		return response;
	}

	// 주식잔고조회_실현손익
	public GetStockBalanceRealizedProfitAndLossResponse getBalanceRealizedProfitAndLoss(Member member) {
		String uri = "/uapi/domestic-stock/v1/trading/inquire-balance-rlz-pl";

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, kisProperties.getGetBalanceRealizedProfitAndLossTrId());
		headers.put("custtype", "P"); // 고객타입(P: 개인, B: 기업)

		// 파라미터 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("CANO", member.getComprehensiveAccountNumber()); // 종합계좌번호
		params.add("ACNT_PRDT_CD", member.getAccountProductCode()); // 계좌상품코드
		params.add("AFHR_FLPR_YN", "N"); // 시간외단일가여부 (기본값 N)
		params.add("OFL_YN", ""); // 오프라인여부
		params.add("INQR_DVSN", "00"); // 조회구분 (기본값 00)
		params.add("UNPR_DVSN", "01"); // 단가구분 (기본값 01)
		params.add("FUND_STTL_ICLD_YN", "N"); // 펀드결제포함여부 (기본값 N)
		params.add("FNCG_AMT_AUTO_RDPT_YN", "N"); // 융자금액자동상환여부 (기본값 N)
		params.add("PRCS_DVSN", "00"); // PRCS_DVSN (기본값 00)
		params.add("COST_ICLD_YN", ""); // 비용포함여부
		params.add("CTX_AREA_FK100", ""); // 연속조회검색조건100 (최초 조회시 공란)
		params.add("CTX_AREA_NK100", ""); // 연속조회키100 (최초 조회시 공란)

		GetStockBalanceRealizedProfitAndLossResponse response = webClientUtil.getFromKis(
			headers,
			uri,
			params,
			GetStockBalanceRealizedProfitAndLossResponse.class);

		checkResponse(response);

		return response;
	}

	// 주식 현재가 조회
	public GetStockPriceResponse getStockPrice(Member member, String productNumber) {
		String uri = "/uapi/domestic-stock/v1/quotations/inquire-price";

		// 헤더 설정
		Map<String, String> headers = makeHeaders(member, kisProperties.getGetPriceTrId());

		// 파라미터 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("FID_COND_MRKT_DIV_CODE", "J"); // 'J': 주식, ETF, ETN / 'W': ELW
		params.add("FID_INPUT_ISCD", productNumber); // FID 입력 종목코드: 6자리 종목번호 또는 ETN의 경우 'Q'로 시작하는 코드

		GetStockPriceResponse response = webClientUtil.getFromKis(headers, uri, params, GetStockPriceResponse.class);

		checkResponse(response);

		return response;
	}
}

