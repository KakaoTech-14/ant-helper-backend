package kakaobootcamp.backend.domains.key.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kakaobootcamp.backend.domains.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Salt {

	@Id
	@Column(name = "key_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	KeyType keyType;

	@Column(nullable = false)
	private String value;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Salt(KeyType keyType, String value, Member member) {
		this.keyType = keyType;
		this.value = value;
		setMember(member);
	}

	private void setMember(Member member) {
		member.getSalt().add(this);
		this.member = member;
	}
}
