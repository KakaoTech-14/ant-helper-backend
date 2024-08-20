package kakaobootcamp.backend.domains.member;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.common.util.encoder.PasswordEncoderService;
import kakaobootcamp.backend.domains.email.EmailService;
import kakaobootcamp.backend.domains.email.domain.EmailCode;
import kakaobootcamp.backend.domains.email.repository.EmailCodeRepository;
import kakaobootcamp.backend.domains.email.repository.VerifiedEmailRepository;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.SendVerificationCodeRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.VerifyEmailCodeRequest;
import kakaobootcamp.backend.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoderService passwordEncoderService;

	private final EmailService emailService;
	private final EmailCodeRepository emailCodeRepository;
	private final VerifiedEmailRepository verifiedEmailRepository;

	private static final String EMAIL_TITLE = "ANT HELPER 이메일 인증 코드";
	private static final String EMAIL_TEXT = "인증 코드는 %d 입니다.";

	// 회원가입하기
	@Transactional(rollbackFor = CustomException.class)
	public void createMember(CreateMemberRequest request) {
		String email = request.getEmail();

		// 이메일 검증
		validateDuplicatedEmail(email);
		emailService.validateVerifiedEmail(email);

		// 패스워드 암호화
		String encodedPassword = passwordEncoderService.encodePassword(request.getPw());
		request.setPw(encodedPassword);

		// 회원 저장
		saveMember(request);

		// 이메일 삭제
		emailService.deleteVerifiedEmail(email);
	}

	// 회원 저장하기
	@Transactional
	public void saveMember(CreateMemberRequest request) {
		MemberRole memberRole = MemberRole.USER; // 기본 권한은 USER
		Member member = Member.create(request, memberRole);
		memberRepository.save(member);
	}

	// 이메일 중복 조회
	private void validateDuplicatedEmail(String email) {
		boolean isDuplicated = memberRepository.existsByEmail(email);
		if (isDuplicated) {
			throw CustomException.from(ErrorCode.EMAIL_DUPLICATE);
		}
	}

	// 이메일 인증번호 보내기
	@Transactional(rollbackFor = CustomException.class)
	public void validateEmailAndSendEmailVerification(SendVerificationCodeRequest request) {
		String email = request.getEmail();

		validateDuplicatedEmail(email);

		// 본문
		Integer verificationCode = emailService.createVerificationCode();
		String text = String.format(EMAIL_TEXT, verificationCode);

		// 이메일 보내기
		emailService.sendEmail(email, EMAIL_TITLE, text);

		// 이메일 코드 저장
		emailService.checkEmailCodeDuplicationAndSaveEmailCode(email, verificationCode);
	}

	// 이메일 인증 확인
	@Transactional(rollbackFor = CustomException.class)
	public boolean verityEmailCode(VerifyEmailCodeRequest request) {
		String email = request.getEmail();
		Integer requestCode = request.getCode();

		// 이메일로 인증 코드 검색
		EmailCode emailCode = emailCodeRepository.findByEmail(email)
			.orElseThrow(() -> CustomException.from(ErrorCode.UNAUTHENTICATED_EMAIL));

		// 인증 코드 비교 후 인증된 이메일 저장
		if (Objects.equals(emailCode.getCode(), requestCode)) {
			emailService.saveVerifiedEmail(email);
			return true;
		}
		return false;
	}

	// 회원 삭제하기
	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}
}
