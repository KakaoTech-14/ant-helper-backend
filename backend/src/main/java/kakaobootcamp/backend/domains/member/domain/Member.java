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
import kakaobootcamp.backend.domains.transaction.domain.Transaction;
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
	@Column(nullable = false, length = 1000)
	private String appKey;

	@Setter
	@Column(nullable = false, length = 1000)
	private String secretKey;

	@Setter
	private String approvalKey;

	@Column(nullable = false)
	private String appKeySalt;

	@Column(nullable = false)
	private String secretKeySalt;

	@Column(nullable = false)
	private String comprehensiveAccountNumber;

	@Column(nullable = false)
	private String accountProductCode;

	@Enumerated(EnumType.STRING)
	@Setter
	private AutoTradeState autoTradeState;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transactions = new ArrayList<>();

	@Builder
	private Member(
		String email,
		String pw,
		MemberRole memberRole,
		String appKey,
		String secretKey,
		String appKeySalt,
		String secretKeySalt,
		AutoTradeState autoTradeState,
		String comprehensiveAccountNumber,
		String accountProductCode) {
		this.email = email;
		this.pw = pw;
		this.memberRole = memberRole;
		this.appKey = appKey;
		this.secretKey = secretKey;
		this.appKeySalt = appKeySalt;
		this.secretKeySalt = secretKeySalt;
		this.autoTradeState = autoTradeState;
		this.comprehensiveAccountNumber = comprehensiveAccountNumber;
		this.accountProductCode = accountProductCode;
	}
}