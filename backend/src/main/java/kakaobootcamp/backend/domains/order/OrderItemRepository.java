package kakaobootcamp.backend.domains.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.member.domain.Member;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findAllByMember(Member member);
}
