package kakaobootcamp.backend.domains.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor
	public static class CreateMemberRequest {

		@NotBlank(message = "email은 비어있을 수 없습니다.")
		@Email(message = "email 형식이 올바르지 않습니다.")
		private String email;

		@Setter
		@NotBlank(message = "pw는 비어있을 수 없습니다.")
		private String pw;

		@Setter
		@NotBlank(message = "appkey은 비어있을 수 없습니다.")
		private String appKey;

		@Setter
		@NotBlank(message = "secretkey은 비어있을 수 없습니다.")
		private String secretKey;
	}

	@AllArgsConstructor
	public static class LoginRequest {

		public String email;
		public String pw;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class SendVerificationCodeRequest {

		@NotBlank(message = "email은 비어있을 수 없습니다.")
		@Email(message = "email 형식이 올바르지 않습니다.")
		private String email;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class VerifyEmailCodeRequest {

		@NotBlank(message = "email은 비어있을 수 없습니다.")
		@Email(message = "email 형식이 올바르지 않습니다.")
		private String email;

		private Integer code;
	}
}
