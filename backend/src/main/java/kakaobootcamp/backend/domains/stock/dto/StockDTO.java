package kakaobootcamp.backend.domains.stock.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StockDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor
	@Builder
	public static class OrderStockRequest {

		private String CANO; // 종합계좌번호 (8자리)

		private String ACNT_PRDT_CD; // 계좌상품코드 (2자리)

		private String PDNO; // 종목코드 (6자리, 최대 12자리)

		private String ORD_DVSN; // 주문구분 (2자리)

		private String ORD_QTY; // 주문수량 (최대 10자리)

		private String ORD_UNPR; // 주문단가 (최대 19자리)
	}

	@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public class OrderStockResponse {

		// 응답 코드
		private String rt_cd;

		// 메시지 코드
		private String msg_cd;

		// 메시지 내용
		private String msg;

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
