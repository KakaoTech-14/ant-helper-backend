package kakaobootcamp.backend.domains.key;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.key.domain.Salt;
import kakaobootcamp.backend.domains.key.domain.KeyType;
import kakaobootcamp.backend.domains.member.domain.Member;

public interface SaltRepository extends JpaRepository<Salt, Long> {

	Optional<Salt> findByKeyTypeAndMember(KeyType keyType, Member member);
}
