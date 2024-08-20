package kakaobootcamp.backend.common.util.memberLoader;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberLoader {

	private final MemberRepository memberRepository;

	// Authentication 객체에서 Member를 찾는 메서드
	public Member getMember() {
		String email = getEmail();

		return memberRepository.findByEmail(email)
			.orElseThrow(() -> CustomException.from(MEMBER_NOT_FOUND));
	}

	// Authentication 객체에서 email을 추출하는 메서드
	public String getEmail() {
		Authentication authentication = SecurityContextHolder
			.getContext()
			.getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();

		return userDetails.getUsername();
	}

}
