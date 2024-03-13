package ru.kduskov.vkapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class UserService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(cacheNames = {"users"}, key = "#id")
    public Optional<User> get(int id) {
        return super.get(id, USERS, User.class);

    }

    @CacheEvict(cacheNames = {"users"}, key = "#id")
    public boolean delete(int id) {
        return super.delete(id, USERS);
    }

    @Cacheable(cacheNames = {"users"}, key = "#incomingUser.id")
    public int create(User incomingUser) {
        User user = super.create(incomingUser,USERS);
        return Objects.isNull(user) ? -1 : user.getId();
    }

    @CachePut(cacheNames = {"users"}, key = "#incomingUser.id")
    public int update(User incomingUser) {
        User user = super.update(incomingUser, incomingUser.getId(), USERS);
        if (Objects.isNull(user)) {
            logger.warn(String.format("User with id %d wasn't updated", incomingUser.getId()));
            return -1;
        }
        else {
            return user.getId();
        }
    }


    @CachePut(cacheNames = {"users_posts"}, key = "#id")
    public Optional<Post> getPosts(int id) {
        logger.info(String.format("Get posts for user with id %d", id));
        return super.getList(id, String.format("%s/%d%s", USERS, id, ExternalApiConstants.POSTS));
    }

    @CachePut(cacheNames = {"users_albums"}, key = "#id")
    public Optional<List<Album>> getAlbums(int id) {
        logger.info(String.format("Get albums for user with id %d", id));
        return super.getList(id, String.format("%s/%d%s", USERS, id, ALBUMS));
    }
}
