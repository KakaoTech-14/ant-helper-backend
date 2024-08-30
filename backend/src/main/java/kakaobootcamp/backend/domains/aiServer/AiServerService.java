package kakaobootcamp.backend.domains.aiServer;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import kakaobootcamp.backend.common.util.webClient.WebClientUtil;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO.GetOrderListResponse;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.transaction.TransactionService;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.GetTransactionDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiServerService {

	private final WebClientUtil webClientUtil;
	private final TransactionService transactionService;

	public GetOrderListResponse getOrderList(Member member) {
		String url = "/api/stocks/evaluation";

		GetTransactionDTO request = transactionService.getTransaction(member);

		return webClientUtil.postFromAiServer(
			new HashMap<>(),
			url,
			request,
			GetOrderListResponse.class);
	}
}
