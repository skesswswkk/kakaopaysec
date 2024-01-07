package com.example.stockranking.keyowrd.repository;


import com.example.stockranking.keyowrd.entity.KeywordCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordCountRepository extends JpaRepository<KeywordCount, Long> {
    Optional<KeywordCount> findKeywordCountByKeyword(String keyword);

    List<KeywordCount> findAllByOrderByCountDesc();
}
