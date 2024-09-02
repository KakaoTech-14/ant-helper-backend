package kakaobootcamp.backend.domains.watchList.dto;

import kakaobootcamp.backend.domains.watchList.domain.WatchList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WatchListDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class FindWatchListResponse {

		private String productNumber;
		private String name;
		private String industry;

		public static FindWatchListResponse from(WatchList watchList) {
			return new FindWatchListResponse(
				watchList.getProductNumber(),
				watchList.getName(),
				watchList.getIndustry());
		}
	}
}
