package kakaobootcamp.backend.domains.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakaobootcamp.backend.domains.member.domain.Member;
import kakaobootcamp.backend.domains.member.domain.MemberRole;
import kakaobootcamp.backend.domains.member.dto.MemberDTO.CreateMemberRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public void createMember(CreateMemberRequest request) {
		MemberRole memberRole = MemberRole.USER;

		Member member = Member.create(request, memberRole);

		memberRepository.save(member);
	}
}
