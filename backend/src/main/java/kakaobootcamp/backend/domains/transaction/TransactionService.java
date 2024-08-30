package kakaobootcamp.backend.domains.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.transaction.domain.Transaction;
import kakaobootcamp.backend.domains.transaction.domain.TransactionItem;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.GetTransactionResponse;
import kakaobootcamp.backend.domains.transaction.dto.TransactionDTO.SaveTransactionRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;

	@Transactional
	public void saveTransaction(Member member, SaveTransactionRequest saveTransactionRequest) {

		Transaction transaction = Transaction.builder()
			.amount(saveTransactionRequest.getAmount())
			.member(member)
			.build();

		// TransactionItem 생성 후 transaction에 넣기
		saveTransactionRequest.getTransactionItems()
			.forEach(element -> TransactionItem.builder()
				.productNumber(element.getProductNumber())
				.name(element.getName())
				.transaction(transaction)
				.build());

		transactionRepository.save(transaction);
	}

	@Transactional
	public void deleteTransaction(Member member) {
		transactionRepository.findByMember(member)
			.ifPresent(transactionRepository::delete);
	}

	public GetTransactionResponse getTransaction(Member member) {
		Transaction transaction = transactionRepository.findByMember(member)
			.orElseThrow(() -> ApiException.from(ErrorCode.TRANSACTION_NOT_FOUND));

		return GetTransactionResponse.from(transaction);
	}
}
