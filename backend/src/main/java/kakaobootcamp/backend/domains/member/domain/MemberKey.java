package kakaobootcamp.backend.domains.member.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class MemberKey {

	private String appKey;
	private String secretKey;
}
