package kakaobootcamp.backend.domains.key;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.key.domain.Salt;
import kakaobootcamp.backend.domains.key.domain.KeyType;
import kakaobootcamp.backend.domains.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaltService {

	private final SaltRepository saltRepository;

	@Transactional
	public void saveSalt(String value, KeyType keyType, Member member) {
		Salt salt = Salt.builder()
			.value(value)
			.keyType(keyType)
			.member(member)
			.build();
		saltRepository.save(salt);
	}

	public Salt findSalt(KeyType keyType, Member member) {
		// 예외처리 불완정
		return saltRepository.findByKeyTypeAndMember(keyType, member)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 키가 없습니다."));
	}
}
