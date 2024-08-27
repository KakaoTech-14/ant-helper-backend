package kakaobootcamp.backend.domains.order.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kakaobootcamp.backend.domains.order.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderDTO {



	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class saveOrdersRequest {

		@Size(min = 1, max = 10, message = "주문은 최소 {min}개, 최대 {max}개까지 가능합니다.")
		private List<OrderElement> orders;

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class OrderElement {

			@NotBlank(message = "productNumber는 비어있을 수 없습니다.")
			private String productNumber;

			@NotBlank(message = "name은 비어있을 수 없습니다.")
			private String name;
		}
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class GetOrderResponse {

		private Long id;
		private String productNumber;
		private String name;

		public static GetOrderResponse from(OrderItem orderItem) {
			return new GetOrderResponse(orderItem.getId(), orderItem.getProductNumber(), orderItem.getName());
		}
	}
}
