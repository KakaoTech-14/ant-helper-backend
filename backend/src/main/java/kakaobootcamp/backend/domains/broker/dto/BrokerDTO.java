package kakaobootcamp.backend.domains.broker.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BrokerDTO {

	@Getter
	@AllArgsConstructor
	public static class GetAccessKeyRequest {

		private String appkey;
		private String secretkey;
		private final String grant_type;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class GetAccessKeyResponse {

		private String approval_key;
	}
}
