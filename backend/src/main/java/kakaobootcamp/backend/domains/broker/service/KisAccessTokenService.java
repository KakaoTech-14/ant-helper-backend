package kakaobootcamp.backend.domains.broker.service;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.KisAccessTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KisAccessTokenService {

	 private final KisAccessTokenRepository kisAccessTokenRepository;

	public Optional<KisAccessToken> findKisAccessToken(Long memberId) {
		return kisAccessTokenRepository.findById(memberId);
	}

	public void saveKisAccessToken(KisAccessToken kisAccessToken) {
		kisAccessTokenRepository.save(kisAccessToken);
	}
}
