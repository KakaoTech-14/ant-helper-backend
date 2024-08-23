package kakaobootcamp.backend.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiException extends RuntimeException {

	private final ErrorCode errorCode;

	public static ApiException from(ErrorCode errorCode) {
		return new ApiException(errorCode);
	}
}