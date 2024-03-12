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
import ru.kduskov.vkapi.model.external_api.Comment;
import ru.kduskov.vkapi.model.external_api.Post;

import java.util.*;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class PostService extends BaseService{
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(cacheNames = {"posts"}, key = "#id")
    public Optional<Post> get(int id) {
        logger.info(String.format("Get post with id %d", id));
        Optional post = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", POSTS, id);
            logger.info("Send POST request to :" + URL + endpoint);

            post = Optional.ofNullable(restTemplate.getForObject(endpoint, Post.class));
        } catch (Exception e) {
            logger.error(String.format("Error while searching for post with id %d: %s", id, e.getMessage()));
        }
        return post;
    }

    @CacheEvict(cacheNames = {"posts"}, key = "#id")
    public boolean delete(int id) {
//        logger.info(String.format("Delete post with id %d", id));
//        try {
//            String endpoint = String.format("%s/%d", POSTS, id);
//            logger.info("Send DELETE request to :" + URL + endpoint);
//
//            restTemplate.delete(endpoint);
//        } catch (Exception e) {
//            logger.error(String.format("Error while deleting post with id %d: %s", id, e.getMessage()));
//            return false;
//        }
//        return true;
        return super.delete(id, "post", POSTS);

    }

    @CachePut(cacheNames = {"posts_comments"}, key = "#id")
    public Optional<List<Comment>> getComments(int id) {
        logger.info(String.format("Get comments for post with id %d", id));

        Optional commentsList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", POSTS, id, COMMENTS);
            logger.info("Send GET request to :" + URL + endpoint);
            ResponseEntity<List<Comment>> response =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {
                            });
            commentsList = Optional.of(response.getBody());
        }
        catch (Exception e) {
            logger.warn(String.format("Comments for post with id %d wasn't found cause of exception %s", id, e.getMessage()));
        }
        return commentsList;
    }

    @Cacheable(cacheNames = {"posts"}, key = "#incomingPost.id")
    public int create(Post incomingPost) {
        logger.info(String.format("Create post with id %d", incomingPost.getId()));

        HttpEntity<Post> request = new HttpEntity<>(incomingPost);
        logger.info("Send POST request to :" + URL + POSTS);
        Post post = restTemplate.postForObject(POSTS, request, Post.class);

        return post.getId();
    }

    @CachePut(cacheNames = {"posts"}, key = "#incomingPost.id")
    public int update(Post incomingPost) {
        logger.info(String.format("Update post with id %d", incomingPost.getId()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String endpoint = String.join("/",POSTS, String.valueOf(incomingPost.getId()));
        logger.info("Send PUT request to :" + URL + endpoint);
        ResponseEntity<Post> response = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                new HttpEntity<Post>(incomingPost),
                Post.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getId();
        } else {
            logger.warn(String.format("Post with id %d wasn't updated", incomingPost.getId()));
            return -1;
        }
    }
}
