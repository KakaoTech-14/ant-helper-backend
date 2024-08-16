package kakaobootcamp.backend.domains.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String loginId;

	@Column(nullable = false)
	private String pw;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole memberRole;

	@Column(nullable = false)
	private String appKey;

	@Column(nullable = false)
	private String secretKey;

	@Setter
	private String approvalKey;

	private Member(String loginId, String pw, String email, MemberRole memberRole, String appKey, String secretKey) {
		this.loginId = loginId;
		this.pw = pw;
		this.email = email;
		this.memberRole = memberRole;
		this.appKey = appKey;
		this.secretKey = secretKey;
	}

	public static Member create(CreateMemberRequest request, MemberRole memberRole) {
		return new Member(
			request.getLoginId(),
			request.getPw(),
			request.getEmail(),
			memberRole,
			request.getAppKey(),
			request.getSecretKey()
		);
	}
}