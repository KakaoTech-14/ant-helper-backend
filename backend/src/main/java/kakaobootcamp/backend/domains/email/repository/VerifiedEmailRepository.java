package kakaobootcamp.backend.domains.email.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kakaobootcamp.backend.domains.email.domain.VerifiedEmail;

public interface VerifiedEmailRepository  extends CrudRepository<VerifiedEmail, String> {

	Optional<VerifiedEmail> findByEmail(String email);
}
