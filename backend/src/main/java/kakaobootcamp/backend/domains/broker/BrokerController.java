package kakaobootcamp.backend.domains.broker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kakaobootcamp.backend.common.dto.DataResponse;
import kakaobootcamp.backend.common.dto.ErrorResponse;
import kakaobootcamp.backend.common.util.memberLoader.MemberLoader;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO.GetAccessKeyRequest;
import kakaobootcamp.backend.domains.broker.dto.BrokerDTO.GetAccessKeyResponse;
import kakaobootcamp.backend.domains.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Tag(name = "BROKER API", description = "증권사에 대한 API입니다.")
@RestController
@RequestMapping("/api/broker")
@RequiredArgsConstructor
public class BrokerController {

	private final BrokerService brokerService;
	private final MemberLoader memberLoader;

	@PostMapping("/access-key")
	@Operation(
		summary = "Approval Key 요청",
		description = "Approval Key를 받아오기 위한 API입니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "성공"
			),
		}
	)
	public ResponseEntity<DataResponse<Void>> getApprovalKey() {
		Member member = memberLoader.getMember();

		brokerService.getAndSaveApprovalKey(member);

		return ResponseEntity.ok(DataResponse.ok());
	}
}
