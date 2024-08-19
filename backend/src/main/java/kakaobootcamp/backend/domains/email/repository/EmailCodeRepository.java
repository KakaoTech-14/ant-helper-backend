package kakaobootcamp.backend.domains.email.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kakaobootcamp.backend.domains.email.domain.EmailCode;

public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {

	Optional<EmailCode> findByEmail(String email);
}
