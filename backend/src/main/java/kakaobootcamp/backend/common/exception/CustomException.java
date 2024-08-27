package kakaobootcamp.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException{

	private final HttpStatus httpStatus;
	private final String message;

	public static CustomException from(HttpStatus httpStatus, String message) {
		return new CustomException(httpStatus, message);
	}
}
