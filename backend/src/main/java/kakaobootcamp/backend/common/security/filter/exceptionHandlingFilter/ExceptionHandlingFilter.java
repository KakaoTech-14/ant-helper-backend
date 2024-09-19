package kakaobootcamp.backend.common.security.filter.exceptionHandlingFilter;

import java.io.IOException;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.exception.ErrorCode;
import kakaobootcamp.backend.common.util.responseWriter.ResponseWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandlingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (ApiException e) {
			log.warn("ExceptionHandlingFilter: {}", e.getMessage());

			ErrorResponse errorResponse = ErrorResponse.of(e.getHttpStatus(), e.getMessage());
			ResponseWriter.writeResponse(response, errorResponse, e.getHttpStatus());
		} catch (Exception e) {
			log.warn("ExceptionHandlingFilter: {}", e.getMessage());
			ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

			ErrorResponse errorResponse = ErrorResponse.of(errorCode, List.of(e.getMessage()));

			ResponseWriter.writeResponse(response, errorResponse, errorCode.getHttpStatus());
		}
	}
}

