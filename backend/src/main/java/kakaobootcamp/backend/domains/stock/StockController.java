package kakaobootcamp.backend.domains.stock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.StockDTO;
import lombok.RequiredArgsConstructor;

@Tag(name = "STOCK API", description = "주식에 대한 API입니다.")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

	private final MemberLoader memberLoader;
	private final StockService stockService;

	@PostMapping("/buy")
	public ResponseEntity<DataResponse<Void>> buyStock() {
		Member member = memberLoader.getMember();

		StockDTO.OrderStockRequest request = StockDTO.OrderStockRequest.builder()
			.CANO("")
			.ACNT_PRDT_CD("")
			.PDNO("005930")
			.ORD_DVSN("01")
			.ORD_QTY("1")
			.ORD_UNPR("0")
			.build();

		stockService.orderStock(request, member);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/sell")
	public ResponseEntity<?> sellStock() {

		return ResponseEntity.ok().build();
	}

	@GetMapping("/recommendations")
	public ResponseEntity<?> getStockRecommendations() {

		return ResponseEntity.ok().build();
	}
}
