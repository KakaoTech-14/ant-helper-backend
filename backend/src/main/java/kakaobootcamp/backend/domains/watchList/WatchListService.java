package kakaobootcamp.backend.domains.watchList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.watchList.domain.WatchList;
import kakaobootcamp.backend.domains.watchList.dto.WatchListDTO.FindWatchListResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WatchListService {

	private final WatchListRepository watchListRepository;

	// 관심 목록 조회
	public Page<FindWatchListResponse> findWatchLists(Member member, Pageable pageable) {
		Page<WatchList> watchLists = watchListRepository.findAllByMember(member, pageable);

		return watchLists.map(FindWatchListResponse::from);
	}
}
