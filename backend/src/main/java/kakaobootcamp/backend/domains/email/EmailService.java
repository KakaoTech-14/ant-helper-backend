package kakaobootcamp.backend.domains.email;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.email.domain.EmailCode;
import kakaobootcamp.backend.domains.email.domain.VerifiedEmail;
import kakaobootcamp.backend.domains.email.repository.EmailCodeRepository;
import kakaobootcamp.backend.domains.email.repository.VerifiedEmailRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;
	private final VerifiedEmailRepository verifiedEmailRepository;
	private final EmailCodeRepository emailCodeRepository;

	public void sendEmail(
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
	public void saveVerifiedEmail(String email) {
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
	public Integer createVerificationCode() {
		Random random = new Random();

		return random.nextInt(900000) + 100000; // 100000부터 999999까지의 숫자 생성
	}

	// 이메일 코드 중복 확인 및 저장
	@Transactional
	public void checkEmailCodeDuplicationAndSaveEmailCode(String email, Integer verificationCode) {
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
