package kakaobootcamp.backend.domains.watchList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.watchList.dto.WatchListDTO.FindWatchListsResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WatchListService {

	private final WatchListRepository watchListRepository;

	@Transactional
	public void deleteWatchList(Long watchListId) {
		watchListRepository.deleteById(watchListId);
	}

	public Page<FindWatchListsResponse> findWatchLists(Member member, Pageable pageable) {
		Page<WatchList> watchLists = watchListRepository.findAllByMember(member, pageable);

		return watchLists.map(FindWatchListsResponse::of);
	}
}
