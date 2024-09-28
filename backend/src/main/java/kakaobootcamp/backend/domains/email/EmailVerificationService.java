package kakaobootcamp.backend.domains.email;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.email.repository.EmailTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
	private final EmailTokenRepository emailTokenRepository;

	// 인증된 토큰 삭제
	@Transactional
	public void deleteEmailToken(String token) {
		emailTokenRepository.deleteById(token);
	}

	// 이메일 토큰 확인 및 삭제
	public void verifyEmailToken(String token) {
		if (!isTokenValid(token)) {
			throw ApiException.from(ErrorCode.UNAUTHENTICATED_EMAIL);
		}
		deleteEmailToken(token);
	}

	// 토큰의 유효성을 확인하는 메서드
	private boolean isTokenValid(String token) {
		return emailTokenRepository.findById(token).isPresent();
	}
}