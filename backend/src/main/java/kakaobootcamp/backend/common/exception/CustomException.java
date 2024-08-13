package kakaobootcamp.backend.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public static CustomException from(ErrorCode errorCode) {
		return new CustomException(errorCode);
	}
}