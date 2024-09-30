package kakaobootcamp.backend.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "kis")
public class KisProperties {

	private String url;
	private Domestic domestic;
	private International international;

	@Getter
	@Setter
	public static class Domestic {
		private String buyTrId;
		private String sellTrId;
		private String findBalanceTrId;
		private String findBalanceRealizedProfitAndLossTrId;
		private String findPriceTrId;
		private String findDomesticStockPriceChartId;
	}

	@Getter
	@Setter
	public static class International {
		private String buyTrId;
		private String sellTrId;
		private String findBalanceTrId;
		private String findBalanceRealizedProfitAndLossTrId;
		private String findPriceTrId;
		private String findDomesticStockPriceChartId;
	}
}
