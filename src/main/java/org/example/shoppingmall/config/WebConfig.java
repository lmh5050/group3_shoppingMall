package org.example.shoppingmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/product/**")
                .addResourceLocations("classpath:/static/images/product/");
    }

    @Bean
    public WebClient webClient() {
        // FastAPI가 8000번 포트에서 실행 중이므로, baseUrl을 8000으로!
        return WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

}
