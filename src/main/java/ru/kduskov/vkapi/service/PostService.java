package ru.kduskov.vkapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Comment;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;
import ru.kduskov.vkapi.repository.AuditRecordRepository;

import java.util.*;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class PostService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(RestTemplate restTemplate, AuditRecordRepository auditRep) {
        this.restTemplate = restTemplate;
        this.auditRep = auditRep;
    }

    @Cacheable(cacheNames = {"posts"}, key = "#id")
    public Optional<Post> get(int id) {
        return super.get(id, POSTS, Post.class);
    }

    @CacheEvict(cacheNames = {"posts"}, key = "#id")
    public boolean delete(int id) {
        return super.delete(id, POSTS);
    }

    @Cacheable(cacheNames = {"posts"}, key = "#incomingPost.id")
    public int create(Post incomingPost) {
        Post post = super.create(incomingPost, POSTS);
        return Objects.isNull(post) ? -1 : post.getId();
    }

    @CachePut(cacheNames = {"posts"}, key = "#incomingPost.id")
    public int update(Post incomingPost) {
        Post post = super.update(incomingPost, incomingPost.getId(), POSTS);
        if (Objects.isNull(post)) {
            logger.warn(String.format("Post with id %d wasn't updated", incomingPost.getId()));
            return -1;
        } else {
            return post.getId();
        }
    }

    @CachePut(cacheNames = {"posts_comments"}, key = "#id")
    public Optional<List<Comment>> getComments(int id) {
        logger.info(String.format("Get comments for post with id %d", id));
        Optional list = super.getList(id, String.format("%s/%d%s", POSTS, id, COMMENTS));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Comment>>() {}));
    }

    @CachePut(cacheNames = {"users_posts"}, key = "#userId")
    public Optional<List<Post>> getPostsByUser(Integer userId) {
        Optional list =  super.getList(userId, String.format("%s?userId=%d", POSTS, userId));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Post>>() {}));
    }
}
