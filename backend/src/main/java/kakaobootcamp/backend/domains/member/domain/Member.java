package kakaobootcamp.backend.domains.member.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import kakaobootcamp.backend.domains.key.domain.Salt;
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
	private String email;

	@Column(nullable = false)
	private String pw;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole memberRole;

	@Column(nullable = false)
	private String appKey;

	@Column(nullable = false)
	private String secretKey;

	@Setter
	private String approvalKey;

	@OneToMany(mappedBy = "member", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Salt> salt = new ArrayList<>();

	private Member(String email, String pw, MemberRole memberRole, String appKey, String secretKey) {
		this.email = email;
		this.pw = pw;
		this.memberRole = memberRole;
		this.appKey = appKey;
		this.secretKey = secretKey;
	}

	public static Member of(CreateMemberRequest request, MemberRole memberRole) {
		return new Member(
			request.getEmail(),
			request.getPw(),
			memberRole,
			request.getAppKey(),
			request.getSecretKey()
		);
	}
}