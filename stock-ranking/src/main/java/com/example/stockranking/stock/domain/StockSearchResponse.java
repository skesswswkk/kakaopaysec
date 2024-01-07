package com.example.stockranking.stock.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
@Schema(description = "종목코드 검색 결과")
public class StockSearchResponse {
    @Schema(description = "종목코드") private String itemCode;
    @Schema(description = "종목명") private String stockName;
    @Schema(description = "등락률") private String fluctuationsRatio;
    @Schema(description = "거래량") private String accumulatedTradingVolume;

    public StockSearchResponse(String itemCode, String stockName, String fluctuationsRatio, String accumulatedTradingVolume) {
    }

    public static StockSearchResponse of(String itemCode, String stockName, String fluctuationsRatio, String accumulatedTradingVolume) {
        return new StockSearchResponse(itemCode, stockName, fluctuationsRatio, accumulatedTradingVolume);
    }
}