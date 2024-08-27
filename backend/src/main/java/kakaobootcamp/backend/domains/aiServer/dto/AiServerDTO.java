package kakaobootcamp.backend.domains.aiServer.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AiServerDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor
	public static class GetOrderListRequest {

		List<StockOrderDTO> stocks;

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		@AllArgsConstructor
		public static class StockOrderDTO {
			private String productNumber;
			private String name;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class GetOrderListResponse {

		private List<Output> outputs;

		@Getter
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		@AllArgsConstructor
		public static class Output {
			private String productNumber;
			private String name;
			private int percentage;
		}
	}
}
