package kakaobootcamp.backend.domains.email.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "memberEmail", timeToLive =60*60)
public class VerifiedEmail {

	@Id
	private String email;
}
