package kakaobootcamp.backend.domains.autoTrade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.aiServer.AiServerService;
import kakaobootcamp.backend.domains.aiServer.dto.AiServerDTO.GetOrderListResponse;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.stock.StockService;
import kakaobootcamp.backend.domains.stock.dto.StockDTO.OrderStockRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoTradeService {

	private final AiServerService aiServerService;
	private final StockService stockService;
	private final MemberService memberService;

	private void doAutoTradeForMember(Member member) {
		memberService.checkAutoTradeState(member);

		GetOrderListResponse orderList = aiServerService.getOrderList(member);

		// 기존 주문 내역과 비교

		// 주문
		orderList.getElements()
			.forEach(element -> {
				OrderStockRequest request = OrderStockRequest.builder()
					.PDNO(element.getProductNumber())
					.ORD_QTY(String.valueOf(element.getQuantity()))
					.ORD_DVSN("01")
					.ORD_UNPR("0")
					.build();
				stockService.orderStock(member, request);
			});
	}

	// 자동 거래를 ON한 멤버들에 대해 자동 거래를 수행
	public void doAutoTradeForAutoTradeMembers() {
		memberService.findAllAutoTradeOnMember()
			.forEach(this::doAutoTradeForMember);
	}
}
