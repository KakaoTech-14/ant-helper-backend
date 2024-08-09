package kakaobootcamp.backend.domains.autoTrade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "AUTO TRADE API", description = "자동거래에 대한 API입니다.")
@RestController
@RequestMapping("/api/autotrades")
@RequiredArgsConstructor
public class AutoTradeController {

	@PostMapping("/start")
	public ResponseEntity<?> startAutoTrade() {

		return ResponseEntity.ok().build();
	}

	@PostMapping("/end")
	public ResponseEntity<?> endAutoTrade() {

		return ResponseEntity.ok().build();
	}
}
