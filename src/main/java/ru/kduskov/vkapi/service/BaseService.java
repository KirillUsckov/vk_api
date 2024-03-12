package ru.kduskov.vkapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.model.external_api.Post;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.POSTS;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.URL;

@Service
public class BaseService {
    private final Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    protected RestTemplate restTemplate;

    public boolean delete(int id, String entity, String endpointPart) {
        logger.info(String.format("Delete %s with id %d", entity, id));
        try {
            String endpoint = String.format("%s/%d", endpointPart, id);
            logger.info("Send DELETE request to :" + URL + endpoint);

            restTemplate.delete(endpoint);
        } catch (Exception e) {
            logger.error(String.format("Error while deleting %s with id %d: %s", entity, id, e.getMessage()));
            return false;
        }
        return true;
    }

    public <T> T create(T input, int id, String endpoint) {
        logger.info(String.format("Create post with id %d", id));

        HttpEntity<T> request = new HttpEntity<>(input);
        logger.info("Send POST request to :" + URL + POSTS);

        return (T) restTemplate.postForObject(endpoint, request, input.getClass());
    }
}
