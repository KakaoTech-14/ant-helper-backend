package kakaobootcamp.backend.domains.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	List<Member> findAllByAutoTradeState(AutoTradeState autoTradeState);
}
