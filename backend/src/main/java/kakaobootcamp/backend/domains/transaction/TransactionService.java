package kakaobootcamp.backend.domains.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.transaction.domain.Transaction;
import kakaobootcamp.backend.domains.transaction.domain.TransactionItem;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.FindTransactionResponse;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.FindTransactionResponse.Element;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.SaveTransactionRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;

	// 거래 저장
	@Transactional
	public void saveTransaction(Member member, SaveTransactionRequest saveTransactionRequest) {
		// 거래 존재 확인
		checkTransactionExistence(member);

		Transaction transaction = Transaction.builder()
			.amount(saveTransactionRequest.getAmount())
			.member(member)
			.build();

		// TransactionItem 생성 후 transaction에 넣기
		saveTransactionRequest.getTransactionItems()
			.forEach(element -> TransactionItem.builder()
				.productNumber(element.getProductNumber())
				.name(element.getName())
				.industry(element.getIndustry())
				.transaction(transaction)
				.build());

		transactionRepository.save(transaction);
	}

	// 거래 존재 확인
	private void checkTransactionExistence(Member member) {
		boolean existence = transactionRepository.existsByMember(member);

		if (existence) {
			throw ApiException.from(ErrorCode.TRANSACTION_DUPLICATE);
		}
	}

	// 거래 삭제
	@Transactional
	public void deleteTransaction(Member member) {
		transactionRepository.findByMember(member)
			.ifPresent(transactionRepository::delete);
	}

	// 회원 거래 조회
	public FindTransactionResponse findTransaction(Member member) {
		return transactionRepository.findByMember(member)
			.map(transaction -> {
				List<Element> elements = transaction.getTransactionItems().stream()
					.map(Element::from)
					.toList();
				return FindTransactionResponse.of(true, transaction.getAmount(), elements);
			})
			.orElseGet(() -> FindTransactionResponse.of(false, null, null));
	}

	// 멤버의 자동거래 상태에 따른 거래 조회
	public List<Transaction> getAllTransactionsByMemberAutoTrade(AutoTradeState autoTradeState) {
		return transactionRepository.findAllByMember_AutoTradeState(autoTradeState);
	}
}
