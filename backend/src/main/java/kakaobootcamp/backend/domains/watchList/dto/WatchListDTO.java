package kakaobootcamp.backend.domains.watchList.dto;

import kakaobootcamp.backend.domains.watchList.WatchList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WatchListDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class FindWatchListsResponse {

		public static FindWatchListsResponse of(WatchList watchList) {
			//이 클래스 내용 추가되어야 함.
			return new FindWatchListsResponse();
		}
	}
}
