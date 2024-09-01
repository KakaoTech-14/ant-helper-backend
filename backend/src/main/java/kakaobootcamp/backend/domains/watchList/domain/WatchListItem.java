package kakaobootcamp.backend.domains.watchList.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WatchListItem {

	@Id
	@Column(name = "watch_list_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productNumber;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String industry;


	@ManyToOne
	@JoinColumn(name = "watch_list_id")
	private WatchList watchList;
}
