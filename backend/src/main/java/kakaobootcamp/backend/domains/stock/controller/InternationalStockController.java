package kakaobootcamp.backend.domains.stock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.dto.InternationalStockDTO.InternationalOrderStockRequest;
import kakaobootcamp.backend.domains.stock.service.InternationalStockService;
import lombok.RequiredArgsConstructor;

@Tag(name = "INTERNATIONAL STOCK API", description = "해외 주식에 대한 API입니다.")
@RestController
@RequestMapping("/api/stock/international")
@RequiredArgsConstructor
public class InternationalStockController {

	private final MemberLoader memberLoader;
	private final InternationalStockService internationalStockService;

	// 삭제될 API
	@PostMapping("/buy")
	public ResponseEntity<DataResponse<Void>> buyStock(InternationalOrderStockRequest request) {
		Member member = memberLoader.getMember();

		internationalStockService.orderStock(member, request, true);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/sell")
	public ResponseEntity<DataResponse<Void>> sellStock(InternationalOrderStockRequest request) {
		Member member = memberLoader.getMember();

		internationalStockService.orderStock(member, request, false);

		return ResponseEntity.ok(DataResponse.ok());
	}
}
