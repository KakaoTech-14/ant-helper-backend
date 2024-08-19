package kakaobootcamp.backend.domains.member;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.domains.member.dto.MemberDTO;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.LoginRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "MEMBER API", description = "회원에 대한 API입니다.")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

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

	@GetMapping("/login-id/{loginId}/duplicate")
	@Operation(
		summary = "id 중복 조회",
		description = "id가 중복되면 true, 중복되지 않으면 false를 반환",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			)
		}
	)
	public ResponseEntity<DataResponse<Boolean>> checkLoginIdDuplicate(@PathVariable("loginId") String loginId) {
		boolean isDuplicate = memberService.getEmailDuplicate(loginId);

		return ResponseEntity.ok(DataResponse.from(isDuplicate));
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
	public ResponseEntity<DataResponse<Void>> loginMember(LoginRequest request) {
		// 이 메소드는 실제로 실행되지 않습니다. 문서용도로만 사용됩니다.
		return ResponseEntity.ok(DataResponse.ok());
	}



	@DeleteMapping
	public ResponseEntity<?> deleteMember() {

		// 이 부분 jwt 토큰에서 memberId를 추출하여 사용하도록 변경해야 함
		memberService.deleteMember(1L);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutMember() {

		return ResponseEntity.ok().build();
	}

	@GetMapping("/stocks/watchlist")
	public ResponseEntity<?> getWatchList() {

		return ResponseEntity.ok().build();
	}

	@PostMapping("/stocks/watchlist")
	public ResponseEntity<?> addWatchList() {

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("stocks/watchlist/{watchListId}")
	public ResponseEntity<?> deleteWatchList(@PathVariable("watchListId") Long watchListId) {

		return ResponseEntity.ok().build();
	}

	@GetMapping("/stocks/purchases")
	public ResponseEntity<?> getPurchases() {

		return ResponseEntity.ok().build();
	}

	@PostMapping("/stocks/purchases")
	public ResponseEntity<?> addPurchase() {

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("stocks/purchases/{purchaseId}")
	public ResponseEntity<?> deletePurchase(@PathVariable("purchaseId") Long purchaseId) {

		return ResponseEntity.ok().build();
	}
}
