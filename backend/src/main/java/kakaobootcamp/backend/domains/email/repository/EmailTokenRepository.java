package kakaobootcamp.backend.domains.email.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kakaobootcamp.backend.domains.email.domain.EmailToken;

public interface EmailTokenRepository extends CrudRepository<EmailToken, String> {
}
