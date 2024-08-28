package kakaobootcamp.backend.domains.member;

import java.util.Objects;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.common.util.encoder.PasswordEncoderUtil;
import kakaobootcamp.backend.domains.email.EmailService;
import kakaobootcamp.backend.domains.email.domain.EmailCode;
import kakaobootcamp.backend.domains.email.repository.EmailCodeRepository;
import kakaobootcamp.backend.common.util.encoder.EncryptUtil;
import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.LogoutToken;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.domain.RefreshToken;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.SendVerificationCodeRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.VerifyEmailCodeRequest;
import kakaobootcamp.backend.domains.member.repository.LogoutRepository;
import kakaobootcamp.backend.domains.member.repository.MemberRepository;
import kakaobootcamp.backend.domains.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoderUtil passwordEncoderUtil;

	private final EmailService emailService;
	private final EmailCodeRepository emailCodeRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final LogoutRepository logoutRepository;

	private static final String EMAIL_TITLE = "ANT HELPER 이메일 인증 코드";
	private static final String EMAIL_TEXT = "인증 코드는 %d 입니다.";

	// 회원가입하기
	@Transactional(rollbackFor = ApiException.class)
	public void createMember(CreateMemberRequest request) {
		String email = request.getEmail();

		// 이메일 검증
		validateDuplicatedEmail(email);
		emailService.validateVerifiedEmail(email);

		// 비밀번호 암호화
		String password = passwordEncoderUtil.encodePassword(request.getPw());

		// appKey 암호화
		SecretKey appKeySalt = EncryptUtil.generateKey();
		String encryptedAppKey = EncryptUtil.encrypt(request.getAppKey(), appKeySalt);

		// secretKey 암호화
		SecretKey secretKeySalt = EncryptUtil.generateKey();
		String encryptedSecretKey = EncryptUtil.encrypt(request.getSecretKey(), secretKeySalt);

		log.info(encryptedSecretKey);
		// 회원 저장
		Member member = Member.builder()
			.email(email)
			.pw(password)
			.memberRole(MemberRole.USER) // 기본 권한은 USER
			.appKey(encryptedAppKey)
			.secretKey(encryptedSecretKey)
			.appKeySalt(EncryptUtil.keyToString(appKeySalt))
			.secretKeySalt(EncryptUtil.keyToString(secretKeySalt))
			.autoTradeState(AutoTradeState.OFF)
			.comprehensiveAccountNumber(request.getComprehensiveAccountNumber())
			.accountProductCode(request.getAccountProductCode())
			.build();

		memberRepository.save(member);

		// 이메일 삭제
		emailService.deleteVerifiedEmail(email);
	}

	// 이메일 중복 조회
	private void validateDuplicatedEmail(String email) {
		boolean isDuplicated = memberRepository.existsByEmail(email);
		if (isDuplicated) {
			throw ApiException.from(ErrorCode.EMAIL_DUPLICATE);
		}
	}

	// 이메일 인증번호 보내기
	@Transactional(rollbackFor = ApiException.class)
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
	@Transactional(rollbackFor = ApiException.class)
	public boolean verityEmailCode(VerifyEmailCodeRequest request) {
		String email = request.getEmail();
		Integer requestCode = request.getCode();

		// 이메일로 인증 코드 검색
		EmailCode emailCode = emailCodeRepository.findByEmail(email)
			.orElseThrow(() -> ApiException.from(ErrorCode.UNAUTHENTICATED_EMAIL));

		// 인증 코드 비교 후 인증된 이메일 저장
		if (Objects.equals(emailCode.getCode(), requestCode)) {
			emailService.saveVerifiedEmail(email);
			return true;
		}
		return false;
	}

	// 회원 삭제하기
	@Transactional
	public void deleteMember(Member member) {
		memberRepository.delete(member);
	}

	// AppKey 조회
	public String getDecryptedAppKey(Member member) {
		String appKey = member.getAppKey();
		String appKeySalt = member.getAppKeySalt();

		return EncryptUtil.decrypt(appKey, EncryptUtil.stringToKey(appKeySalt));
	}

	// SecretKey 조회
	public String getDecryptedSecretKey(Member member) {
		String secretKey = member.getSecretKey();
		String secretKeySalt = member.getSecretKeySalt();

		return EncryptUtil.decrypt(secretKey, EncryptUtil.stringToKey(secretKeySalt));
	}

	//Approval Key 저장
	@Transactional
	public void updateApprovalKey(Member member, String approvalKey) {
		member.setApprovalKey(approvalKey);
	}

	//로그아웃
	@Transactional
	public void logoutMember(Member member, String accessToken) {
		Long memberId = member.getId();

		// 회원의 refreshToken 삭제
		RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId).orElse(null);
		refreshTokenRepository.deleteById(refreshToken.getRefreshToken());

		// 같은 accessToken으로 다시 로그인하지 못하도록 블랙리스트에 저장
		logoutRepository.save(new LogoutToken(UUID.randomUUID().toString(), accessToken));
	}

	// 자동 거래 상태 변경
	@Transactional
	public void updateAutoTradeState(Member member, AutoTradeState autoTradeState) {
		member.setAutoTradeState(autoTradeState);
	}
}
