package kakaobootcamp.backend.domains.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByLoginId(String loginId);
}
