package kakaobootcamp.backend.domains.broker.service;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;
import static kakaobootcamp.backend.domains.broker.dto.BrokerDTO.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.domains.broker.KisAccessToken;
import kakaobootcamp.backend.domains.broker.KisAccessTokenRepository;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO.*;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.Member;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrokerService {

	private final WebClient webClient;
	private final MemberService memberService;
	private final KisAccessTokenService kisAccessTokenService;

	// 한국 투자 증권에서 Approval Key를 받아와서 저장하는 메서드
	@Transactional
	public void getAndSaveApprovalKey(Member member) {
		String appKey = memberService.getDecryptedAppKey(member);
		String secretKey = memberService.getDecryptedSecretKey(member);

		GetAccessKeyResponse approvalKey = getApprovalKey(appKey, secretKey);

		memberService.updateApprovalKey(member, approvalKey.getApproval_key());
	}

	// Approval Key를 받아오는 메서드
	private GetAccessKeyResponse getApprovalKey(String appKey, String secretKey) {
		String grantType = "client_credentials";
		GetAccessKeyRequest request = new GetAccessKeyRequest(appKey, secretKey, grantType);

		return webClient
			.post()
			.uri("/oauth2/Approval")
			.body(Mono.just(request), GetAccessKeyRequest.class)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomException.from(INVALID_KEY)))
			.bodyToMono(GetAccessKeyResponse.class)
			.block();
	}

	// 한국 투자 증권에서 Access Token을 받아와서 저장하는 메서드
	@Transactional
	public void getAndSaveAccessToken(Member member) {
		String appKey = memberService.getDecryptedAppKey(member);
		String secretKey = memberService.getDecryptedSecretKey(member);

		GetAccessTokenResponse accessToken = getAccessToken(appKey, secretKey);

		// 1.기존 accessToken이 있으면 재발급
		// 2.기존 accessToken이 없으면 새로 생성
		KisAccessToken kisAccessToken = kisAccessTokenService.findKisAccessToken(member.getId()).orElse(null);
		if (kisAccessToken == null) {
			kisAccessToken = new KisAccessToken(member.getId(), accessToken.getAccess_token());
		} else {
			kisAccessToken.setAccessToken(accessToken.getAccess_token());
		}

		kisAccessTokenService.saveKisAccessToken(kisAccessToken);
	}

	// Access Token을 받아오는 메서드
	private GetAccessTokenResponse getAccessToken(String appKey, String secretKey) {
		String grantType = "client_credentials";
		GetAccessTokenRequest request = new GetAccessTokenRequest(appKey, secretKey, grantType);

		return webClient
			.post()
			.uri("/oauth2/tokenP")
			.body(Mono.just(request), GetAccessTokenRequest.class)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomException.from(INVALID_KEY)))
			.bodyToMono(GetAccessTokenResponse.class)
			.block();
	}
}
