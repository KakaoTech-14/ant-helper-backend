package kakaobootcamp.backend.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String issuer;
	private String secret;
	private Access access;
	private Refresh refresh;

	private String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private String MEMBER_ID_CLAIM = "member_id";
	private String MEMBER_ROLE_CLAIM = "member_role";
	private String BEARER = "Bearer ";

	@Getter
	@Setter
	public static class Access {
		private int expiration;
		private String header;
	}

	@Getter
	@Setter
	public static class Refresh {
		private int expiration;
		private String header;
	}
}
