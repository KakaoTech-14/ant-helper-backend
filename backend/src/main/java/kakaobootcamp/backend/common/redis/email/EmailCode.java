package kakaobootcamp.backend.common.redis.email;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "emailCode", timeToLive =5*60)
public class EmailCode {

	@Id
	private String email;

	private Integer code;
}
