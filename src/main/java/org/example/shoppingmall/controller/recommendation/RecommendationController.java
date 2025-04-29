package org.example.shoppingmall.controller.recommendation;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.recommendation.ProductResponseDto;
import org.example.shoppingmall.dto.recommendation.RecommendationRequestDto;
import org.example.shoppingmall.dto.recommendation.RecommendationResponseDto;
import org.example.shoppingmall.service.recommendation.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<List<ProductResponseDto>> recommend(@RequestBody RecommendationRequestDto requestDto) {
        List<ProductResponseDto> items = recommendationService.getRecommendedItems(requestDto);
        return ResponseEntity.ok(items);
    }
}
