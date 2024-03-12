package ru.kduskov.vkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external_api.Post;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private RestTemplate restTemplate;

    public Optional<Post> get(int id){
        Optional post = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", ExternalApiConstants.POSTS, id);
            post = Optional.ofNullable(restTemplate.getForObject(endpoint, Post.class));
        }
        catch (Exception e) {
        }
        return post;
    }
}
