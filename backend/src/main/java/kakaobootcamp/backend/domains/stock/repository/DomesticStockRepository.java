package kakaobootcamp.backend.domains.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kakaobootcamp.backend.domains.stock.domain.DomesticStock;

public interface DomesticStockRepository extends JpaRepository<DomesticStock, Long> {

}
