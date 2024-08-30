package kakaobootcamp.backend.domains.aiServer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakaobootcamp.backend.common.dto.DataResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-server")
public class AiServerController {

	private final AiServerService aiServerService;
}
