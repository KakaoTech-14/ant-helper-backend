package kakaobootcamp.backend.domains.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "MEMBER API", description = "회원에 대한 API입니다.")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	@PostMapping
	public ResponseEntity<?> createMember() {

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
