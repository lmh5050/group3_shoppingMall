package org.example.shoppingmall.repository.recommendation;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendationRepository {
    List<Map<String, Object>> selectRecommendCandidates();
}
