package kakaobootcamp.backend.common.security.filter.loginFilter;

import static kakaobootcamp.backend.common.exception.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.exception.ApiException;
import kakaobootcamp.backend.common.security.filter.jwtFilter.JwtTokenProvider;
import kakaobootcamp.backend.common.util.responseWriter.ResponseWriter;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final static String EMAIL_PARAMETER = "email";
	private final static String PW_PARAMETER = "pw";

	private final JwtTokenProvider jwtTokenProvider;

	private final MemberRepository memberRepository;

	public LoginFilter(JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.memberRepository = memberRepository;

		setFilterProcessesUrl("/api/members/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		log.info("LoginFilter");

		String email = obtainEmail(request);
		String password = obtainPw(request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password,
			null);

		return getAuthenticationManager().authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult) {
		String email = extractEmail(authResult);

		// 회원 id 찾기
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> ApiException.from(MEMBER_NOT_FOUND));
		Long memberId = member.getId();

		String accessToken = jwtTokenProvider.createAccessToken(memberId);
		String refreshToken = jwtTokenProvider.createRefreshToken();

		jwtTokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtTokenProvider.updateRefreshToken(memberId, refreshToken);

		log.info("로그인 성공: {}", email);
		log.info("accessToken={}", accessToken);
		log.info("refreshToken={}", refreshToken);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed) {
		String email = obtainEmail(request);
		log.info("로그인 실패: {}", email);

		ResponseWriter.writeResponse(response, DataResponse.ok(), HttpStatus.OK); // 보안을 위해 로그인 실패해도 200리턴
	}

	@Nullable
	protected String obtainEmail(HttpServletRequest request) {
		return request.getParameter(EMAIL_PARAMETER);
	}

	@Nullable
	protected String obtainPw(HttpServletRequest request) {
		return request.getParameter(PW_PARAMETER);
	}

	private String extractEmail(Authentication authentication) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();

		return userDetails.getUsername();
	}
}
