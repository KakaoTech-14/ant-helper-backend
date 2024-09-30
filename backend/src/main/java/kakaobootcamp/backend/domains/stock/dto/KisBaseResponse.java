package kakaobootcamp.backend.domains.stock.dto;

import lombok.Getter;

@Getter
public class KisBaseResponse {

	private String rt_cd; // 응답 코드
	private String msg_cd; // 메시지 코드
	private String msg1; // 메시지 내용
}
