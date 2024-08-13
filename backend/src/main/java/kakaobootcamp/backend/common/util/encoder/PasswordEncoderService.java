package kakaobootcamp.backend.common.util.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordEncoderService {

	private final PasswordEncoder passwordEncoder;

	// 패스워드 인코딩
	public String encodePassword(String rawPw) {
		return passwordEncoder.encode(rawPw);
	}

	// 요청된 pw와 서버의 pw가 일치하는 지 확인
	public boolean matchPassword(String rawPw, String pw) {
		return passwordEncoder.matches(rawPw, pw);
	}
}
