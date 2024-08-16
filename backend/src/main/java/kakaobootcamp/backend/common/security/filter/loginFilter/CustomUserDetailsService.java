package kakaobootcamp.backend.common.security.filter.loginFilter;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kakaobootcamp.backend.domains.member.MemberRepository;
import kakaobootcamp.backend.domains.member.domain.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));

		String memberId = member.getId().toString(); // 회원 login id를 감추기 위해 pk 사용
		String pw = member.getPw();
		String role = member.getMemberRole().getValue();

		return User.builder()
			.username(memberId)
			.password(pw)
			.roles(role)
			.build();
	}
}
