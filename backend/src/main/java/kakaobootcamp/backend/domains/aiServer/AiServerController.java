package kakaobootcamp.backend.domains.aiServer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO.GetOrderListRequest.StockOrderDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-server")
public class AiServerController {

	private final AiServerService aiServerService;

	@GetMapping
	public ResponseEntity<DataResponse<Void>> get() {

		// 이 부분 추후 변경
		List<StockOrderDTO> stocks = List.of(
			new StockOrderDTO("005930", "삼성전자"),
			new StockOrderDTO("066570", "LG전자")
		);

		AiServerDTO.GetOrderListRequest request = new AiServerDTO.GetOrderListRequest(stocks);

		aiServerService.getOrderList(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

}
