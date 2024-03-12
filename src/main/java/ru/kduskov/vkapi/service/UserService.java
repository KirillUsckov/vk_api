package ru.kduskov.vkapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external_api.Album;
import ru.kduskov.vkapi.model.external_api.Post;
import ru.kduskov.vkapi.model.external_api.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class UserService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

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

    @CacheEvict(cacheNames = {"users"}, key = "#id")
    public boolean delete(int id) {
        return super.delete(id, "users", USERS);
    }

    @Cacheable(cacheNames = {"users"}, key = "#incomingUser.id")
    public int create(User incomingUser) {
//        logger.info(String.format("Create post with id %d", incomingPost.getId()));
//
//        HttpEntity<Post> request = new HttpEntity<>(incomingPost);
//        logger.info("Send POST request to :" + URL + POSTS);
//        Post post = restTemplate.postForObject(POSTS, request, Post.class);

        User user = super.create(incomingUser, incomingUser.getId(),USERS);
        return user.getId();
    }

    @CachePut(cacheNames = {"users"}, key = "#incomingUser.id")
    public int update(User incomingUser) {
        logger.info(String.format("Update user with id %d", incomingUser.getId()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String endpoint = String.join("/",POSTS, String.valueOf(incomingUser.getId()));
        logger.info("Send PUT request to :" + URL + endpoint);
        ResponseEntity<User> response = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                new HttpEntity<User>(incomingUser),
                User.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getId();
        } else {
            logger.warn(String.format("Post with id %d wasn't updated", incomingUser.getId()));
            return -1;
        }
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
