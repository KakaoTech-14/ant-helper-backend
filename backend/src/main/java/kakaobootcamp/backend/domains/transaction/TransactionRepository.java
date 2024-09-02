package kakaobootcamp.backend.domains.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.transaction.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<Transaction> findByMember(Member member);

	boolean existsByMember(Member member);

	List<Transaction> findAllByMember_AutoTradeState(AutoTradeState autoTradeState);
}
