package kakaobootcamp.backend.domains.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDTO {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class CreateMemberRequest {

		@NotBlank(message = "loginId는 비어있을 수 없습니다.")
		@Email(message = "email 형식이 올바르지 않습니다.")
		private String email;

		@Setter
		@NotBlank(message = "pw는 비어있을 수 없습니다.")
		private String pw;

		@NotBlank(message = "appkey은 비어있을 수 없습니다.")
		private String appKey;

		@NotBlank(message = "secretkey은 비어있을 수 없습니다.")
		private String secretKey;
	}

	public static class LoginRequest {
		public String email;
		public String pw;
	}
}
