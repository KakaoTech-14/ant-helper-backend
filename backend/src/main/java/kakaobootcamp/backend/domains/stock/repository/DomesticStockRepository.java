package kakaobootcamp.backend.domains.stock.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kakaobootcamp.backend.domains.stock.domain.DomesticStock;

public interface DomesticStockRepository extends JpaRepository<DomesticStock, Long> {

	@Query("SELECT d FROM DomesticStock d WHERE d.name LIKE %:keyword%")
	List<DomesticStock> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
