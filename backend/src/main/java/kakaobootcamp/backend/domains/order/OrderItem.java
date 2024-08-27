package kakaobootcamp.backend.domains.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class OrderItem {

	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productNumber;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	private OrderItem(String productNumber, String name, Member member) {
		this.productNumber = productNumber;
		this.name = name;
		setMember(member);
	}

	private void setMember(Member member) {
		this.member = member;
		member.getOrderItems().add(this);
	}
}
