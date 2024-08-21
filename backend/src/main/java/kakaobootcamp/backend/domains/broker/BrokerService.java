package kakaobootcamp.backend.domains.broker;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;
import static kakaobootcamp.backend.domains.broker.dto.BrokerDTO.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import kakaobootcamp.backend.common.exception.CustomException;
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

	// 한국 투자 증권에서 Approval Key를 받아와서 저장하는 메서드
	@Transactional
	public void getAndSaveApprovalKey(Member member) {
		String appKey = memberService.getDecryptedAppKey(member);
		String secretKey = memberService.getDecryptedSecretKey(member);

		GetAccessKeyResponse approvalKey = getApprovalKey(appKey, secretKey);

		memberService.updateApprovalKey(member, approvalKey.getApproval_key());
	}

	// Approval Key를 받아오는 메서드
	public GetAccessKeyResponse getApprovalKey(String appKey, String secretKey) {
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
}
