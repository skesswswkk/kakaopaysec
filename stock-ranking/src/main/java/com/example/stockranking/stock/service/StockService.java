package com.example.stockranking.stock.service;

import com.example.stockranking.keyowrd.service.PopularKeywordService;
import com.example.stockranking.stock.config.StockConfig;
import com.example.stockranking.stock.domain.StockFullSearchResponse;
import com.example.stockranking.stock.domain.StockSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.stockranking.keyowrd.event.SearchedKeywordEvent;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {

    private final WebClient webClient;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final StockConfig stockConfig;
    private final Random random = new Random();
    private final PopularKeywordService popularKeywordService;
    public StockSearchResponse searchStock(String stockCode){

        String stockName = stockConfig.getItems()
                .stream()
                .filter(stockItem -> stockCode.equals(stockItem.getCode()))
                .findFirst()
                .map(StockConfig.StockItem::getName)
                .orElse(null);

        if (stockName != null) {
            applicationEventPublisher.publishEvent(new SearchedKeywordEvent(stockName));
        }

        return search(String.format("%06d", Integer.parseInt(stockCode)));
    }

    public List<StockSearchResponse> IncreaseRateService(int page, int size) {

        List<CompletableFuture<StockSearchResponse>> futures = searchStocksAsync();
        List<StockSearchResponse> sortedResponses = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures
                        .stream()
                        .map(CompletableFuture::join)
                        .sorted(Comparator.comparing(response -> new BigDecimal(((StockSearchResponse) response).getFluctuationsRatio())).reversed())
                        .collect(Collectors.toList())
                ).join();

        return paginateResults(sortedResponses, page, size);
    }

    public List<StockSearchResponse> DecreaseRateService(int page, int size) {

        List<CompletableFuture<StockSearchResponse>> futures = searchStocksAsync();
        List<StockSearchResponse> sortedResponses = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .sorted(Comparator.comparing(response -> new BigDecimal((response).getFluctuationsRatio())))
                        .collect(Collectors.toList())
                ).join();

        return paginateResults(sortedResponses, page, size);
    }

    public List<StockSearchResponse> FluctuationRateService(int page, int size) {

        List<CompletableFuture<StockSearchResponse>> futures = searchStocksAsync();
        List<StockSearchResponse> sortedResponses = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .sorted(Comparator.comparing(response -> Integer.parseInt(((StockSearchResponse) response).getAccumulatedTradingVolume().replace(",", ""))).reversed())
                        .collect(Collectors.toList())
                ).join();

        return paginateResults(sortedResponses, page, size);
    }

    public Object RandomTest(int page, int size){
        int apiIndex = random.nextInt(4);

        if(apiIndex == 0) return popularKeywordService.getTopPopularKeywords();
        else if(apiIndex == 1) return IncreaseRateService(page, size);
        else if(apiIndex == 2) return DecreaseRateService(page, size);
        else return FluctuationRateService(page, size);
    }

    public List<CompletableFuture<StockSearchResponse>> searchStocksAsync() {

        List<String> stockCodes = stockConfig.getItems().stream()
                .map(StockConfig.StockItem::getCode)
                .collect(Collectors.toList());

        return stockCodes.stream()
                .map(stockCode -> String.format("%06d", Integer.parseInt(stockCode)))
                .map(stockCode -> CompletableFuture.supplyAsync(() -> search(stockCode)))
                .collect(Collectors.toList());
    }

    public StockSearchResponse search(String stockCode){
        String apiUrl = "https://polling.finance.naver.com/api/realtime/domestic/stock/{stockCode}";

        StockFullSearchResponse fullResponse = webClient.get()
                .uri(apiUrl, stockCode)
                .retrieve()
                .bodyToMono(StockFullSearchResponse.class)
                .block();

        StockSearchResponse stockSearchResponse = fullResponse.getDatas().get(0);

        return stockSearchResponse;
    }

    private List<StockSearchResponse> paginateResults(List<StockSearchResponse> sortedResponses, int page, int size) {
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, sortedResponses.size());

        return sortedResponses.subList(startIndex, endIndex);
    }

}
