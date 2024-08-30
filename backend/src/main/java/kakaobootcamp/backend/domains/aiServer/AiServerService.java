package kakaobootcamp.backend.domains.aiServer;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import kakaobootcamp.backend.common.util.webClient.WebClientUtil;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO.GetOrderListRequest;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO.GetOrderListResponse;
import kakaobootcamp.backend.domains.transaction.TransactionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiServerService {

	private final WebClientUtil webClientUtil;
	private final TransactionService transactionService;

	public GetOrderListResponse getOrderList(GetOrderListRequest request) {
		String url = "/api/stocks/evaluation";

		return webClientUtil.postFromAiServer(
			new HashMap<>(),
			url,
			request,
			GetOrderListResponse.class);
	}
}
