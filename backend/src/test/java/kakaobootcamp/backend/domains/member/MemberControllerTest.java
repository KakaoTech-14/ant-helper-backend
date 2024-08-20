package kakaobootcamp.backend.domains.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kakaobootcamp.backend.domains.email.EmailService;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;

@DisplayName("MemberController 테스트")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EmailService emailService;

	@Nested
	@DisplayName("회원가입")
	class createMember {

		private final String url = "/api/members/signup";

		@Test
		@DisplayName("회원가입 성공 테스트")
		void 회원가입_성공() throws Exception {
			//given
			CreateMemberRequest request = new CreateMemberRequest(
				"a@naver.com",
				"1234",
				"1234",
				"1234");

			String body = objectMapper.writeValueAsString(request);

			//when
			emailService.saveVerifiedEmail(request.getEmail());
			mockMvc.perform(post(url)
					.content(body)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
			//then
		}

		@Test
		@DisplayName("이메일 인증을 하지 않으면 회원가입에 실패한다.")
		void 회원가입_실패1() throws Exception {
			//given
			CreateMemberRequest request = new CreateMemberRequest(
				"b@naver.com",
				"1234",
				"1234",
				"1234");

			String body = objectMapper.writeValueAsString(request);

			//when & then
			mockMvc.perform(post(url)
					.content(body)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
		}

		@Test
		@DisplayName("이메일 형식이 올바르지 않으면 회원가입에 실패한다.")
		void 회원가입_실패2() throws Exception {
			//given
			CreateMemberRequest request = new CreateMemberRequest(
				"a",
				"1234",
				"1234",
				"1234");

			String body = objectMapper.writeValueAsString(request);

			//when & then
			mockMvc.perform(post(url)
					.content(body)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		}
	}

	@Test
	void checkLoginIdDuplicate() {
	}

	@Test
	void loginMember() {
	}

	@Test
	void deleteMember() {
	}

	@Test
	void logoutMember() {
	}

	@Test
	void getWatchList() {
	}

	@Test
	void addWatchList() {
	}

	@Test
	void deleteWatchList() {
	}

	@Test
	void getPurchases() {
	}

	@Test
	void addPurchase() {
	}

	@Test
	void deletePurchase() {
	}
}