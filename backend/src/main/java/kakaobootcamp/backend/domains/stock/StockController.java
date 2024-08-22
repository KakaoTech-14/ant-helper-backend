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
	public ResponseEntity<DataResponse<Void>> buyStock() {
		Member member = memberLoader.getMember();

		StockDTO.OrderStockRequest request = new StockDTO.OrderStockRequest("64470093", "01", "005930", "01", "1", "0");

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
