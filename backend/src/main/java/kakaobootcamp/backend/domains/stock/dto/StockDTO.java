package kakaobootcamp.backend.domains.stock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class StockDTO {

	@Getter
	@Setter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class OrderStockRequest {

		@JsonProperty("PDNO")
		private String PDNO; // 종목코드 (6자리, 최대 12자리)

		@JsonProperty("ORD_DVSN")
		private String ORD_DVSN; // 주문구분 (2자리)

		@JsonProperty("ORD_QTY")
		private String ORD_QTY; // 주문수량 (최대 10자리)

		@JsonProperty("ORD_UNPR")
		private String ORD_UNPR; // 주문단가 (최대 19자리)
	}

	@Getter
	@Setter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class KisOrderStockRequest {

		@JsonProperty("CANO")
		private String CANO; // 종합계좌번호 (8자리)

		@JsonProperty("ACNT_PRDT_CD")
		private String ACNT_PRDT_CD; // 계좌상품코드 (2자리)

		@JsonProperty("PDNO")
		private String PDNO; // 종목코드 (6자리, 최대 12자리)

		@JsonProperty("ORD_DVSN")
		private String ORD_DVSN; // 주문구분 (2자리)

		@JsonProperty("ORD_QTY")
		private String ORD_QTY; // 주문수량 (최대 10자리)

		@JsonProperty("ORD_UNPR")
		private String ORD_UNPR; // 주문단가 (최대 19자리)
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class OrderStockResponse {

		// 응답 코드
		private String rt_cd;

		// 메시지 코드
		private String msg_cd;

		// 메시지 내용
		private String msg1;

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

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class GetStockBalanceResponse {

		private String rt_cd; // 성공 실패 여부 (0: 성공, 0 이외의 값: 실패)
		private String msg_cd; // 응답코드
		private String msg1; // 응답메세지
		private String ctx_area_fk100; // 연속조회검색조건100
		private String ctx_area_nk100; // 연속조회키100
		private List<Output1> output1; // 응답상세1
		private List<Output2> output2; // 응답상세2

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Output1 {
			private String pdno; // 상품번호 (종목번호 뒷 6자리)
			private String prdt_name; // 상품명 (종목명)
			private String trad_dvsn_name; // 매매구분명 (매수매도구분)
			private String bfdy_buy_qty; // 전일매수수량
			private String bfdy_sll_qty; // 전일매도수량
			private String thdt_buyqty; // 금일매수수량
			private String thdt_sll_qty; // 금일매도수량
			private String hldg_qty; // 보유수량
			private String ord_psbl_qty; // 주문가능수량
			private String pchs_avg_pric; // 매입평균가격 (매입금액 / 보유수량)
			private String pchs_amt; // 매입금액
			private String prpr; // 현재가
			private String evlu_amt; // 평가금액
			private String evlu_pfls_amt; // 평가손익금액 (평가금액 - 매입금액)
			private String evlu_pfls_rt; // 평가손익율
			private String evlu_erng_rt; // 평가수익율 (미사용항목, 0으로 출력)
			private String loan_dt; // 대출일자 (INQR_DVSN(조회구분)을 01(대출일별)로 설정해야 값이 나옴)
			private String loan_amt; // 대출금액
			private String stln_slng_chgs; // 대주매각대금
			private String expd_dt; // 만기일자
			private String fltt_rt; // 등락율
			private String bfdy_cprs_icdc; // 전일대비증감
			private String item_mgna_rt_name; // 종목증거금율명
			private String grta_rt_name; // 보증금율명
			private String sbst_pric; // 대용가격 (증권매매의 위탁보증금으로서 현금 대신에 사용되는 유가증권 가격)
			private String stck_loan_unpr; // 주식대출단가
		}

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Output2 {
			private String dnca_tot_amt; // 예수금총금액 (예수금)
			private String nxdy_excc_amt; // 익일정산금액 (D+1 예수금)
			private String prvs_rcdl_excc_amt; // 가수도정산금액 (D+2 예수금)
			private String cma_evlu_amt; // CMA평가금액
			private String bfdy_buy_amt; // 전일매수금액
			private String thdt_buy_amt; // 금일매수금액
			private String nxdy_auto_rdpt_amt; // 익일자동상환금액
			private String bfdy_sll_amt; // 전일매도금액
			private String thdt_sll_amt; // 금일매도금액
			private String d2_auto_rdpt_amt; // D+2자동상환금액
			private String bfdy_tlex_amt; // 전일제비용금액
			private String thdt_tlex_amt; // 금일제비용금액
			private String tot_loan_amt; // 총대출금액
			private String scts_evlu_amt; // 유가평가금액
			private String tot_evlu_amt; // 총평가금액 (유가증권 평가금액 합계금액 + D+2 예수금)
			private String nass_amt; // 순자산금액
			private String fncg_gld_auto_rdpt_yn; // 융자금자동상환여부 (보유현금에 대한 융자금만 차감여부)
			private String pchs_amt_smtl_amt; // 매입금액합계금액
			private String evlu_amt_smtl_amt; // 평가금액합계금액 (유가증권 평가금액 합계금액)
			private String evlu_pfls_smtl_amt; // 평가손익합계금액
			private String tot_stln_slng_chgs; // 총대주매각대금
			private String bfdy_tot_asst_evlu_amt; // 전일총자산평가금액
			private String asst_icdc_amt; // 자산증감액
			private String asst_icdc_erng_rt; // 자산증감수익율 (데이터 미제공)
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetStockBalanceRealizedProfitAndLossResponse {

		private String rt_cd; // 성공 실패 여부
		private String msg_cd; // 응답코드
		private String msg1; // 응답메세지

		private List<Output1> output1; // 응답상세1 (Object Array)
		private List<Output2> output2; // 응답상세2 (Object)

		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Output1 {
			private String pdno; // 상품번호
			private String prdt_name; // 상품명
			private String trad_dvsn_name; // 매매구분명
			private String bfdy_buy_qty; // 전일매수수량
			private String bfdy_sll_qty; // 전일매도수량
			private String thdt_buyqty; // 금일매수수량
			private String thdt_sll_qty; // 금일매도수량
			private String hldg_qty; // 보유수량
			private String ord_psbl_qty; // 주문가능수량
			private String pchs_avg_pric; // 매입평균가격
			private String pchs_amt; // 매입금액
			private String prpr; // 현재가
			private String evlu_amt; // 평가금액
			private String evlu_pfls_amt; // 평가손익금액
			private String evlu_pfls_rt; // 평가손익율
			private String evlu_erng_rt; // 평가수익율
			private String loan_dt; // 대출일자
			private String loan_amt; // 대출금액
			private String stln_slng_chgs; // 대주매각대금
			private String expd_dt; // 만기일자
			private String stck_loan_unpr; // 주식대출단가
			private String bfdy_cprs_icdc; // 전일대비증감
			private String fltt_rt; // 등락율
		}

		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Output2 {
			private String dnca_tot_amt; // 예수금총금액
			private String nxdy_excc_amt; // 익일정산금액
			private String prvs_rcdl_excc_amt; // 가수도정산금액
			private String cma_evlu_amt; // CMA평가금액
			private String bfdy_buy_amt; // 전일매수금액
			private String thdt_buy_amt; // 금일매수금액
			private String nxdy_auto_rdpt_amt; // 익일자동상환금액
			private String bfdy_sll_amt; // 전일매도금액
			private String thdt_sll_amt; // 금일매도금액
			private String d2_auto_rdpt_amt; // D+2자동상환금액
			private String bfdy_tlex_amt; // 전일제비용금액
			private String thdt_tlex_amt; // 금일제비용금액
			private String tot_loan_amt; // 총대출금액
			private String scts_evlu_amt; // 유가평가금액
			private String tot_evlu_amt; // 총평가금액
			private String nass_amt; // 순자산금액
			private String fncg_gld_auto_rdpt_yn; // 융자금자동상환여부
			private String pchs_amt_smtl_amt; // 매입금액합계금액
			private String evlu_amt_smtl_amt; // 평가금액합계금액
			private String evlu_pfls_smtl_amt; // 평가손익합계금액
			private String tot_stln_slng_chgs; // 총대주매각대금
			private String bfdy_tot_asst_evlu_amt; // 전일총자산평가금액
			private String asst_icdc_amt; // 자산증감액
			private String asst_icdc_erng_rt; // 자산증감수익율
			private String rlzt_pfls; // 실현손익
			private String rlzt_erng_rt; // 실현수익율
			private String real_evlu_pfls; // 실평가손익
			private String real_evlu_pfls_erng_rt; // 실평가손익수익율
		}
	}
}
