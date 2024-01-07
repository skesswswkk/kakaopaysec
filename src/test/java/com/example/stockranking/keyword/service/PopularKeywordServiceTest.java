package com.example.stockranking.keyword.service;

import com.example.stockranking.keyowrd.repository.KeywordCountRepository;
import com.example.stockranking.keyowrd.service.PopularKeywordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.mockito.BDDMockito.*;

@SpringBootTest
class PopularKeywordServiceTest {
    @Autowired
    private PopularKeywordService popularKeywordService;
    @MockBean
    private KeywordCountRepository keywordCountRepository;

    @DisplayName("저장 성공")
    @Test
    void success() {
        //given

        //when
        popularKeywordService.upsertKeywordCounts(anyMap());

        //then
        then(keywordCountRepository)
                .should(times(1))
                .saveAll(anyList());
    }

    @DisplayName("낙관적 락 오류 발생시 재시도 3회")
    @Test
    void optimisticLock() {
        //given
        given(keywordCountRepository.saveAll(anyList()))
                .willThrow(ObjectOptimisticLockingFailureException.class);

        //when
        popularKeywordService.upsertKeywordCounts(anyMap());

        //then
        then(keywordCountRepository)
                .should(times(3))
                .saveAll(anyList());
    }

    @DisplayName("키 중복 오류 발생시 재시도 3회")
    @Test
    void keywordDuplicate() {
        //given
        given(keywordCountRepository.saveAll(anyList()))
                .willThrow(DataIntegrityViolationException.class);

        //when
        popularKeywordService.upsertKeywordCounts(anyMap());

        //then
        then(keywordCountRepository)
                .should(times(3))
                .saveAll(anyList());
    }
}