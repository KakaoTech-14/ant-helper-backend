package kakaobootcamp.backend.domains.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InternationalStockDTO {

	@Data
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class InternationalOrderStockRequest {

		@JsonProperty("CANO")
		private String CANO; // 고객계좌번호

		@JsonProperty("ACNT_PRDT_CD")
		private String ACNT_PRDT_CD; // 계좌상품코드

		@JsonProperty("OVRS_EXCG_CD")
		private String OVRS_EXCG_CD; // 해외거래소코드

		@JsonProperty("PDNO")
		private String PDNO; // 종목코드 (6자리, 최대 12자리)

		@JsonProperty("ORD_QTY")
		private String ORD_QTY; // 주문수량 (최대 10자리)

		@JsonProperty("OVRS_ORD_UNPR")
		private String OVRS_ORD_UNPR; // 해외주문단가 (최대 10자리)

		@JsonProperty("SLL_TYPE")
		private final String SLL_TYPE;

		@JsonProperty("ORD_SVR_DVSN_CD")
		private final String ORD_SVR_DVSN_CD;

		@JsonProperty("ORD_DVSN")
		private String ORD_DVSN;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class OrderStockResponse extends KisBaseResponse {
		// 출력 데이터
		private Output output;

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Output {

			// KRX 전달 주문 원본 번호
			private String KRX_FWDG_ORD_ORGNO;

			// 주문 번호
			private String ODNO;

			// 주문 시간
			private String ORD_TMD;
		}
	}
}
