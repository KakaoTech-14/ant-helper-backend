package kakaobootcamp.backend.common.scheduling;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulingService {

	@Transactional(readOnly = false)
	@Async
	@Scheduled(cron = "0 0 11 * * MON-FRI")
	public void autoOrder() {
	}
}
