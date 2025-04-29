package org.example.shoppingmall.service.recommendation;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.recommendation.ProductResponseDto;
import org.example.shoppingmall.dto.recommendation.RecommendationRequestDto;
import org.example.shoppingmall.dto.recommendation.RecommendationResponseDto;
import org.example.shoppingmall.repository.recommendation.RecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final WebClient webClient;
    private final RecommendationRepository recommendationRepository;

    public List<ProductResponseDto> getRecommendedItems(RecommendationRequestDto requestDto) {
        // 1. 사용자 클러스터 예측 (FastAPI 호출)
        int userCluster = webClient.post()
                .uri("/recommend")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Map.class)
                .block() != null ? ((Number)((Map<?, ?>)webClient.post()
                .uri("/recommend")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Map.class)
                .block()).get("cluster")).intValue() : -1;

        // 2. 추천 후보 상품 전체 조회
        List<Map<String, Object>> candidates = recommendationRepository.selectRecommendCandidates();

        // 3. 후보 상품별로 FastAPI에 클러스터 예측 요청 → 사용자와 같은 클러스터만 필터
        return candidates.stream()
                .filter(item -> {
                    RecommendationRequestDto itemDto = new RecommendationRequestDto();
                    itemDto.setHeight(((Number)item.get("height")).floatValue());
                    itemDto.setWeight(((Number)item.get("weight")).floatValue());
                    itemDto.setSize(((Number)item.get("size")).floatValue());
                    itemDto.setAge(((Number)item.get("age")).intValue());
                    itemDto.setBodyType((String)item.get("body_type"));

                    Integer itemCluster = webClient.post()
                            .uri("/recommend")
                            .bodyValue(itemDto)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block() != null ? ((Number)((Map<?, ?>)webClient.post()
                            .uri("/recommend")
                            .bodyValue(itemDto)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block()).get("cluster")).intValue() : -1;

                    return itemCluster == userCluster;
                })
                .map(item -> {
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setItem_id(((Number)item.get("item_id")).longValue());
                    dto.setItem_description((String)item.get("item_description"));
                    dto.setRating(item.get("rating") != null ? ((Number)item.get("rating")).intValue() : null);
                    dto.setReview_text((String)item.get("review_text"));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}