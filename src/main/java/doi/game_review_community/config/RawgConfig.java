package doi.game_review_community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RawgConfig {
    @Value("${rawg.api.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate rawgRestTemplate(RestTemplateBuilder b) {
        return b
                .rootUri(baseUrl)
                .defaultHeader(HttpHeaders.USER_AGENT, "MyGameApp")
                .build();
    }
}
