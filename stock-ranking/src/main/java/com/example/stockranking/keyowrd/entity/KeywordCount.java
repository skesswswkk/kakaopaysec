package com.example.stockranking.keyowrd.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "keyword_count")
public class KeywordCount {
    @Id
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "keyword_count")
    private Long count;

    private KeywordCount(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    public static KeywordCount of(String keyword) {
        return new KeywordCount(keyword, 0L);
    }

    public void increaseCount(Long count) {
        this.count += count;
    }
}
