package ru.kduskov.vkapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external_api.Album;
import ru.kduskov.vkapi.model.external_api.Post;
import ru.kduskov.vkapi.model.external_api.User;

import java.util.List;
import java.util.Optional;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.URL;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.USERS;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(cacheNames = {"users"}, key = "#id")
    public Optional<User> get(int id) {
        logger.info(String.format("Get user with id %d", id));
        Optional user = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", USERS, id);
            logger.info("Send request to " + URL + endpoint);
            user = Optional.ofNullable(restTemplate.getForObject(endpoint, User.class));
        } catch (Exception e) {
            logger.error(String.format("Error while searching for user with id %d: %s", id, e.getMessage()));
        }
        return user;
    }
    @CachePut(cacheNames = {"users_posts"}, key = "#id")
    public Optional<Post> getPosts(int id) {
        logger.info(String.format("Get posts for user with id %d", id));

        Optional postsList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", USERS, id, ExternalApiConstants.POSTS);
            logger.info("Send request to " + URL + endpoint);

            ResponseEntity<List<Post>> rateResponse =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
                            });
            postsList = Optional.of(rateResponse.getBody());
        } catch (Exception e) {
            logger.error(String.format("Error while searching for posts for user with id %d: %s", id, e.getMessage()));

        }
        return postsList;
    }

    @CachePut(cacheNames = {"users_albums"}, key = "#id")
    public Optional<List<Album>> getAlbums(int id) {
        Optional albumsList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", USERS, id, ExternalApiConstants.ALBUMS);

            ResponseEntity<List<Album>> rateResponse =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Album>>() {
                            });
            albumsList = Optional.of(rateResponse.getBody());
        } catch (Exception e) {
            logger.error(String.format("Error while searching for albums for user with id %d: %s", id, e.getMessage()));

        }
        return albumsList;
    }
}
