package kakaobootcamp.backend.domains.member.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kakaobootcamp.backend.domains.member.domain.LogoutToken;

@Repository
public interface LogoutRepository extends CrudRepository<LogoutToken, String> {

	boolean existsByAccessToken(String accessToken);
}
