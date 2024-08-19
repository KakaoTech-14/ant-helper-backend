package kakaobootcamp.backend.domains.email;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.CustomException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;

	public void sendEmail(String toEmail,
		String title,
		String text)
	{
		SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);

		//메일 전송
		try {
			emailSender.send(emailForm);
		} catch (RuntimeException e) {
			throw CustomException.from(EMAIL_BAD_GATEWAY);
		}
	}

	// 발신할 이메일 데이터 세팅
	private SimpleMailMessage createEmailForm(
		String toEmail,
		String title,
		String text)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(title);
		message.setText(text);

		return message;
	}
}
