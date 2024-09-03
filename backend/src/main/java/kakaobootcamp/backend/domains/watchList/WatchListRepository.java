package kakaobootcamp.backend.domains.watchList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.watchList.domain.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

	Page<WatchList> findAllByMember(Member member, Pageable pageable);

	int countByMember(Member member);
}
