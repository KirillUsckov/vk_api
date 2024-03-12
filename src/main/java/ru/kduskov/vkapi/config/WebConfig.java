package ru.kduskov.vkapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.kduskov.vkapi.constants.ExternalApiConstants;

@Configuration
public class WebConfig {
    @Value(ExternalApiConstants.URL)
    private String apiHost;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiHost));
        return restTemplate;
    }
}
