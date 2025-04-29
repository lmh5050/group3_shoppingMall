package org.example.shoppingmall.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecommendationRequestDto {
    private float height;
    private float weight;
    private float size;
    private int age;

    @JsonProperty("body_type")
    private String bodyType;
}
