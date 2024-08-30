package kakaobootcamp.backend.domains.transaction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionItem {

	@Id
	@Column(name = "transaction_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productNumber;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;

	@Builder
	private TransactionItem(String productNumber, String name, Transaction transaction) {
		this.productNumber = productNumber;
		this.name = name;
		setTransaction(transaction);
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		transaction.getTransactionItems().add(this);
	}
}
