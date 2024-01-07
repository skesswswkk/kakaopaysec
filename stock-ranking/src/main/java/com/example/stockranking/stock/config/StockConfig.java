package com.example.stockranking.stock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "stock")
public class StockConfig {

    private List<StockItem> items;

    public List<StockItem> getItems() {
        return items;
    }

    public void setItems(List<StockItem> items) {
        this.items = items;
    }

    public static class StockItem {
        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
