package kakaobootcamp.backend.domains.stock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "STOCK API", description = "주식에 대한 API입니다.")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

	@PostMapping("/buy")
	public ResponseEntity<?> buyStock() {

		return ResponseEntity.ok().build();
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
