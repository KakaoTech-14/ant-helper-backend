package kakaobootcamp.backend.domains.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.common.util.encoder.PasswordEncoderService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoderService passwordEncoderService;

	// 회원가입하기
	@Transactional
	public void createMember(CreateMemberRequest request) {
		// 로그인 아이디 중복 체크
		checkLoginIdDuplicate(request.getLoginId());

		// 패스워드 암호화
		String encodedPassword = passwordEncoderService.encodePassword(request.getPw());
		request.setPw(encodedPassword);

		MemberRole memberRole = MemberRole.USER;
		Member member = Member.create(request, memberRole);

		memberRepository.save(member);
	}

	private void checkLoginIdDuplicate(String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw CustomException.from(ErrorCode.MEMBER_DUPLICATE);
		}
	}

	// 회원 삭제하기
	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}
}
