package kakaobootcamp.backend.domains.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kakaobootcamp.backend.domains.member.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DomesticStock extends BaseEntity {

	@Id
	@Column(name = "stock_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	String productNumber;

	@Column(nullable = false)
	String name;

	@Builder
	private DomesticStock(String productNumber, String name) {
		this.productNumber = productNumber;
		this.name = name;
	}
}
