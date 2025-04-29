package org.example.shoppingmall.controller.recommendation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationPageController {
    @GetMapping("/recommendation")
    public String showRecommendationPage() {
        return "recommendation/recommendation";
    }
}
