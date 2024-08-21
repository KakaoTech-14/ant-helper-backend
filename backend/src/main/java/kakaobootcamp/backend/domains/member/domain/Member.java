package kakaobootcamp.backend.domains.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
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
	private String email;

	@Column(nullable = false)
	private String pw;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole memberRole;

	@Setter
	@Column(nullable = false)
	private String appKey;

	@Setter
	@Column(nullable = false)
	private String secretKey;

	@Setter
	private String approvalKey;

	private String appKeySalt;

	private String secretKeySalt;

	@Builder
	private Member(String email, String pw, MemberRole memberRole, String appKey, String secretKey, String appKeySalt,
		String secretKeySalt) {
		this.email = email;
		this.pw = pw;
		this.memberRole = memberRole;
		this.appKey = appKey;
		this.secretKey = secretKey;
		this.appKeySalt = appKeySalt;
		this.secretKeySalt = secretKeySalt;
	}
}