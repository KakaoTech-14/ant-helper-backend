package kakaobootcamp.backend.domains.stock.controller;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.FindDomesticStockPriceChartResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.FindSuggestedKeywordResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceRealizedProfitAndLossResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockPriceResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockRequest;
import kakaobootcamp.backend.domains.stock.service.StockService;
import kakaobootcamp.backend.domains.watchList.domain.WatchList;
import kakaobootcamp.backend.domains.watchList.dto.WatchListDTO.FindWatchListResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "STOCK API", description = "주식에 대한 API입니다.")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

	private final MemberLoader memberLoader;
	private final StockService stockService;

	// 삭제될 API
	@PostMapping("/buy")
	public ResponseEntity<DataResponse<Void>> buyStock(OrderStockRequest request) {
		Member member = memberLoader.getMember();

		stockService.orderStock(member, request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/sell")
	public ResponseEntity<DataResponse<Void>> sellStock(OrderStockRequest request) {
		Member member = memberLoader.getMember();

		stockService.sellStock(member, request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("/recommendations")
	public ResponseEntity<DataResponse<List<FindWatchListResponse>>> getStockRecommendations(
		@RequestParam("size") @Min(value = 1, message = "size는 1이상이어야 합니다.") @Max(value = 10, message = "size는 10이하이어야 합니다.") int size,
		@RequestParam("page") @Min(value = 0, message = "page는 0이상이어야 합니다.") int page) {
		Pageable pageable = PageRequest.of(page, size);

		// 더미 데이터
		Member member = memberLoader.getMember();
		List<WatchList> watchLists = member.getWatchLists();
		List<FindWatchListResponse> responses = watchLists.stream()
			.map(FindWatchListResponse::from)
			.toList();

		new PageImpl<>(responses, pageable, watchLists.size());

		return ResponseEntity.ok(DataResponse.from(responses));
	}

	@GetMapping("/balance")
	@Operation(
		summary = "주식 잔고 조회",
		description = """
			주식 잔고 조회 api 입니다.
			처음 요청할 때에는 CTX_AREA_FK100, CTX_AREA_NK100을 비워서 요청해주세요.
			다음 페이지 요청 시 응답에 존재하는 CTX_AREA_FK100, CTX_AREA_NK100을 담아서 요청해주세요.
			""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<GetStockBalanceResponse>> getStockBalance(
		@RequestParam(name = "CTX_AREA_FK100", defaultValue = "") String fk,
		@RequestParam(name = "CTX_AREA_NK100", defaultValue = "") String nk) {
		Member member = memberLoader.getMember();

		GetStockBalanceResponse response = stockService.getStockBalance(member, fk, nk);

		return ResponseEntity.ok(DataResponse.from(response));
	}

	@GetMapping("/balance/realized-profit-and-loss")
	@Operation(
		summary = "실현 손익 조회",
		description = """
			실현 손익 조회 api 입니다.
			실전에서만 사용가능한 api 입니다.""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<GetStockBalanceRealizedProfitAndLossResponse>> getBalanceRealizedProfitAndLoss() {
		Member member = memberLoader.getMember();

		GetStockBalanceRealizedProfitAndLossResponse response = stockService
			.getBalanceRealizedProfitAndLoss(member);

		return ResponseEntity.ok(DataResponse.from(response));
	}

	@GetMapping("/price")
	@Operation(
		summary = "주식 가격 조회",
		description = """
			주식 가격 조회 api 입니다.""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<GetStockPriceResponse>> getStockPrice(
		@RequestParam("productNumber") String productNumber) {
		Member member = memberLoader.getMember();

		GetStockPriceResponse response = stockService.getStockPrice(member, productNumber);

		return ResponseEntity.ok(DataResponse.from(response));
	}

	@GetMapping("/suggested-keywords")
	@Operation(
		summary = "주식 추천 키워드 조회",
		description = """
			주식 추천 키워드 조회 api 입니다.""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<List<FindSuggestedKeywordResponse>>> findSuggestedKeywords(
		@RequestParam("keyword") String keyword) {
		List<FindSuggestedKeywordResponse> responses = stockService.findSuggestedKeywords(keyword);

		return ResponseEntity.ok(DataResponse.from(responses));
	}

	@GetMapping("/update/removed")
	public ResponseEntity<?> updateRemovedStocks() {
		stockService.updateDomesticStocks();

		return ResponseEntity.ok().build();
	}

	@GetMapping("/price-chart")
	@Operation(
		summary = "주식 가격 차트 조회",
		description = """
			주식 가격 차트 조회 api 입니다.
			periodCode는 D, W, M, Y 중 하나여야 합니다.""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "요청 오류입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<FindDomesticStockPriceChartResponse>> findDomesticStockPriceChart(
		@RequestParam("productNumber") String productNumber,
		@Pattern(regexp = "D|W|M|Y", message = "periodCode는 D, W, M, Y 중 하나여야 합니다.") @RequestParam("periodCode") String periodCode) {
		Member member = memberLoader.getMember();

		FindDomesticStockPriceChartResponse response = stockService.findDomesticStockPriceChart(member, productNumber,
			periodCode);

		return ResponseEntity.ok(DataResponse.from(response));
	}
}
