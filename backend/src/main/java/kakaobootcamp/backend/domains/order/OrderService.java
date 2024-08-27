package kakaobootcamp.backend.domains.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.order.dto.OrderDTO.GetOrderResponse;
import kakaobootcamp.backend.domains.order.dto.OrderDTO.saveOrdersRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderItemRepository orderItemRepository;

	@Transactional
	public void saveOrders(Member member, saveOrdersRequest saveOrdersRequest) {
		List<OrderItem> orderItems = saveOrdersRequest.getOrders().stream()
			.map(element -> OrderItem.builder()
				.member(member)
				.productNumber(element.getProductNumber())
				.name(element.getName())
				.build())
			.toList();

		orderItemRepository.saveAll(orderItems);
	}

	@Transactional
	public void deleteOrders(Member member) {
		List<OrderItem> orderItems = orderItemRepository.findAllByMember(member);

		orderItemRepository.deleteAll(orderItems);
	}

	public List<GetOrderResponse> getOrders(Member member) {
		List<OrderItem> orderItems = orderItemRepository.findAllByMember(member);

		return orderItems.stream()
			.map(GetOrderResponse::from)
			.toList();
	}
}
