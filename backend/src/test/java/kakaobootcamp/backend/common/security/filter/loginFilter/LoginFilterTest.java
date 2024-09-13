package kakaobootcamp.backend.common.security.filter.loginFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kakaobootcamp.backend.common.util.encoder.PasswordEncoderConfig;
import kakaobootcamp.backend.common.util.encoder.PasswordEncoderUtil;
import kakaobootcamp.backend.domains.member.MemberService;
import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.dto.MemberDTO;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.LoginRequest;
import kakaobootcamp.backend.domains.member.repository.MemberRepository;

@SpringBootTest
@ActiveProfiles("test-env")
@AutoConfigureMockMvc
@Transactional
class LoginFilterTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoderUtil passwordEncoderUtil;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void 로그인() throws Exception {
		// given
		Member member = Member.builder()
			.email("a@naver.com")
			.pw(passwordEncoderUtil.encodePassword("1234"))
			.accountProductCode("123400")
			.comprehensiveAccountNumber("01")
			.memberRole(MemberRole.USER)
			.autoTradeState(AutoTradeState.OFF)
			.appKey("appKey")
			.appKeySalt("appKeySalt")
			.secretKey("secretKey")
			.secretKeySalt("secretKey")
			.build();
		memberRepository.save(member);

		Optional<Member> byEmail = memberRepository.findByEmail("a@naver.com");
		System.out.println(byEmail.get().getPw());

		// when && then
		LoginRequest request = new LoginRequest(
			"a@naver.com",
			"1234");

		String body = objectMapper.writeValueAsString(request);

		mockMvc.perform(post("/api/members/login")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			// .andExpect(jsonPath("$.data.accessToken").exists());
	}
}