package kakaobootcamp.backend.domains.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "MEMBER API", description = "회원에 대한 API입니다.")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<DataResponse<Void>> createMember(@RequestBody CreateMemberRequest request) {
		memberService.createMember(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@DeleteMapping
	public ResponseEntity<?> deleteMember() {
		// 이 부분 jwt 토큰에서 memberId를 추출하여 사용하도록 변경해야 함
		memberService.deleteMember(1L);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginMember() {

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
