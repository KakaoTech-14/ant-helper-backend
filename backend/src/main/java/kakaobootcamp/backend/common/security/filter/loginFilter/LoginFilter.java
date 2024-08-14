package kakaobootcamp.backend.common.security.filter.loginFilter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.util.responseWriter.ResponseWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final static String LOGIN_ID_PARAMETER = "loginId";

	public LoginFilter() {

		setFilterProcessesUrl("/api/members/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		log.info("JsonUsernamePasswordAuthenticationFilter");

		String memberId = obtainMemberId(request);
		String password = obtainPassword(request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(memberId, password,
			null);

		return getAuthenticationManager().authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		String memberId = extractMemberId(authResult);

		// 토큰 로직 작성
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {

		ResponseWriter.writeResponse(response, DataResponse.ok(), HttpStatus.OK); // 보안을 위해 로그인 실패해도 200리턴
	}

	@Nullable
	protected String obtainMemberId(HttpServletRequest request) {
		return request.getParameter(LOGIN_ID_PARAMETER);
	}

	private String extractMemberId(Authentication authentication) {
		return (String)authentication.getPrincipal();
	}
}
