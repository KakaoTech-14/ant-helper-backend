package kakaobootcamp.backend.domains.email;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;
import static kakaobootcamp.backend.domains.email.dto.EmailDTO.*;

import java.util.Objects;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.email.domain.EmailCode;
import kakaobootcamp.backend.domains.email.domain.VerifiedEmail;
import kakaobootcamp.backend.domains.email.dto.EmailDTO;
import kakaobootcamp.backend.domains.email.repository.EmailCodeRepository;
import kakaobootcamp.backend.domains.email.repository.VerifiedEmailRepository;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;
	private final VerifiedEmailRepository verifiedEmailRepository;
	private final EmailCodeRepository emailCodeRepository;
	private final MemberService memberService;

	private static final String EMAIL_TITLE = "ANT HELPER 이메일 인증 코드";
	private static final String EMAIL_TEXT = "인증 코드는 %d 입니다.";

	// 이메일 인증번호 보내기
	@Transactional(rollbackFor = ApiException.class)
	public void validateEmailAndSendEmailVerification(SendVerificationCodeRequest request) {
		String email = request.getEmail();

		memberService.validateDuplicatedEmail(email);

		// 본문
		Integer verificationCode = createVerificationCode();
		String text = String.format(EMAIL_TEXT, verificationCode);

		// 이메일 보내기
		sendEmail(email, EMAIL_TITLE, text);

		// 이메일 코드 저장
		checkEmailCodeDuplicationAndSaveEmailCode(email, verificationCode);
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
			saveVerifiedEmail(email);
			return true;
		}
		return false;
	}

	// 이메일 보내기
	private void sendEmail(
		String toEmail,
		String title,
		String text) {
		SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);

		// 이메일 전송
		try {
			emailSender.send(emailForm);
		} catch (RuntimeException e) {
			throw ApiException.from(EMAIL_BAD_GATEWAY);
		}
	}

	// 발신할 이메일 데이터 세팅
	private SimpleMailMessage createEmailForm(
		String toEmail,
		String title,
		String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(title);
		message.setText(text);

		return message;
	}

	// 인증된 이메일 저장
	@Transactional
	private void saveVerifiedEmail(String email) {
		VerifiedEmail verifiedEmail = new VerifiedEmail(email);
		verifiedEmailRepository.save(verifiedEmail);
	}

	// 인증된 이메일 삭제
	@Transactional
	public void deleteVerifiedEmail(String email) {
		verifiedEmailRepository.deleteById(email);
	}

	// 인증된 이메일인지 확인
	public void validateVerifiedEmail(String email) {
		boolean isPresent = verifiedEmailRepository.findByEmail(email).isPresent();
		if (!isPresent) {
			throw ApiException.from(ErrorCode.UNAUTHENTICATED_EMAIL);
		}
	}

	// 인증 코드 생성
	private Integer createVerificationCode() {
		Random random = new Random();

		return random.nextInt(900000) + 100000; // 100000부터 999999까지의 숫자 생성
	}

	// 이메일 코드 중복 확인 및 저장
	@Transactional
	private void checkEmailCodeDuplicationAndSaveEmailCode(String email, Integer verificationCode) {
		EmailCode emailCode = emailCodeRepository.findByEmail(email).orElse(null);

		// 1. 이미 저장된 emailCode가 있으면 code만 변경
		// 2. 없으면 새로 생성
		if (emailCode != null) {
			emailCode.setCode(verificationCode);
		} else {
			emailCode = new EmailCode(email, verificationCode);
		}

		emailCodeRepository.save(emailCode);
	}
}
