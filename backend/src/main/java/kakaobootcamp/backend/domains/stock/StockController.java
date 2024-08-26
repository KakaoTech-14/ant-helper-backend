package kakaobootcamp.backend.domains.stock;

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
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceRealizedProfitAndLossResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.GetStockBalanceResponse;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockRequest;
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

		stockService.orderStock(request, member);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/sell")
	public ResponseEntity<DataResponse<Void>> sellStock(OrderStockRequest request) {
		Member member = memberLoader.getMember();

		stockService.sellStock(request, member);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("/recommendations")
	public ResponseEntity<?> getStockRecommendations() {

		return ResponseEntity.ok().build();
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
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
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
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
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
}
