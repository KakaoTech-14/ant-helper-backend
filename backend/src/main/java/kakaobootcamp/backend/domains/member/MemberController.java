package kakaobootcamp.backend.domains.member;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.common.security.filter.jwtFilter.JwtTokenProvider;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.member.domain.AutoTradeState;
import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.SendVerificationCodeRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.UpdateAutoTradeStateRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.VerifyEmailCodeRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "MEMBER API", description = "회원에 대한 API입니다.")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberLoader memberLoader;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signup")
	@Operation(
		summary = "회원가입",
		description = "사용자 이름, 비밀번호, 이메일, appKey, secretKey를 사용하여 회원가입",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "409",
				description = "이미 존재하는 회원입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> createMember(@RequestBody @Valid CreateMemberRequest request) {
		memberService.createMember(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/email/verification-request")
	@Operation(
		summary = "email 인증 요청",
		description = "email 인증을 위한 이메일을 전송",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "409",
				description = "이미 가입된 이메일입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> sendVerificationCode(
		@RequestBody @Valid SendVerificationCodeRequest request) {
		memberService.validateEmailAndSendEmailVerification(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/email/verification")
	@Operation(
		summary = "email 인증 확인",
		description = """
			인증 요청을 한 email이 없으면 401
			code가 일치하면 true, 일치하지 않으면 false를 반환한다.""",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "401",
				description = "이메일 인증을 시도해주세요.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Boolean>> verifyEmailCode(@RequestBody @Valid VerifyEmailCodeRequest request) {
		boolean isVerified = memberService.verityEmailCode(request);

		return ResponseEntity.ok(DataResponse.from(isVerified));
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@Operation(
		summary = "로그인",
		description = "로그인 성공 시 accessToken, refreshToken을 반환",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "401",
				description = "로그인 실패",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> loginMember(
		@RequestParam("email") String email,
		@RequestParam("pw") String pw) {
		// 이 메소드는 실제로 실행되지 않습니다. 문서용도로만 사용됩니다.
		return ResponseEntity.ok(DataResponse.ok());
	}

	@DeleteMapping
	@Operation(
		summary = "회원 탈퇴",
		description = "회원 탈퇴",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> deleteMember() {
		Member member = memberLoader.getMember();

		memberService.deleteMember(member);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/logout")
	@Operation(
		summary = "로그아웃",
		description = "로그아웃",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> logoutMember(HttpServletRequest request) {
		Member member = memberLoader.getMember();
		String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);

		memberService.logoutMember(member, accessToken);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PatchMapping("/auto-trade")
	@Operation(
		summary = "자동거래 상태 변경",
		description = "자동거래 상태 변경",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
			@ApiResponse(
				responseCode = "401",
				description = "유효하지 않은 액세스 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	public ResponseEntity<DataResponse<Void>> updateAutoTradeState(@RequestBody UpdateAutoTradeStateRequest request) {
		Member member = memberLoader.getMember();
		AutoTradeState autoTradeState = request.getAutoTradeState();

		memberService.updateAutoTradeState(member, autoTradeState);

		return ResponseEntity.ok(DataResponse.ok());
	}
}
