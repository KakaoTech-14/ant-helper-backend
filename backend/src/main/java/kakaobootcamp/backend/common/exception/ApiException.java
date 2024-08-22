package kakaobootcamp.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiException {

	HttpStatus httpStatus;
	String message;

	public static ApiException from(HttpStatus httpStatus, String message) {
		return new ApiException(httpStatus, message);
	}
}
