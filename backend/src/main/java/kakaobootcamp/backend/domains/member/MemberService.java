package kakaobootcamp.backend.domains.member;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.common.redis.email.EmailCode;
import kakaobootcamp.backend.common.redis.email.EmailCodeRepository;
import kakaobootcamp.backend.common.util.encoder.PasswordEncoderService;
import kakaobootcamp.backend.domains.email.EmailService;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.SendVerificationCodeRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoderService passwordEncoderService;

	private final EmailService emailService;
	private final EmailCodeRepository emailCodeRepository;

	private static final String EMAIL_TITLE = "ANT HELPER 이메일 인증 코드";
	private static final String EMAIL_TEXT = "인증 코드는 %d 입니다.";

	// 회원가입하기
	@Transactional
	public void createMember(CreateMemberRequest request) {
		// 로그인 아이디 중복 체크
		validateEmail(request.getEmail());

		// 패스워드 암호화
		String encodedPassword = passwordEncoderService.encodePassword(request.getPw());
		request.setPw(encodedPassword);

		MemberRole memberRole = MemberRole.USER;
		Member member = Member.create(request, memberRole);

		memberRepository.save(member);
	}

	// 이메일 중복 체크
	private void validateEmail(String email) {
		boolean isDuplicate = getEmailDuplicate(email);
		if (isDuplicate) {
			throw CustomException.from(ErrorCode.EMAIL_DUPLICATE);
		}
	}

	// 이메일 중복 조회
	public boolean getEmailDuplicate(String email) {
		return memberRepository.existsByEmail(email);
	}

	// 이메일 인증 보내기
	public void validateEmailAndSendEmailVerification(SendVerificationCodeRequest request) {
		String email = request.getEmail();

		validateEmail(email);

		// 본문
		Integer verificationCode = createVerificationCode();
		String text = String.format(EMAIL_TEXT, verificationCode);

		// 이메일 보내기
		emailService.sendEmail(email, EMAIL_TITLE, text);

		// 인증 코드 저장
		EmailCode emailCode = new EmailCode(email, verificationCode);
		emailCodeRepository.save(emailCode);
	}

	// 인증 코드 생성
	private Integer createVerificationCode() {
		Random random = new Random();

		return random.nextInt(900000) + 100000; // 100000부터 999999까지의 숫자 생성
	}

	// 회원 삭제하기
	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}
}
