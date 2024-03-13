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
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.audit.AuditRecord;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;
import ru.kduskov.vkapi.repository.AuditRecordRepository;
import ru.kduskov.vkapi.utils.GeneralInfo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class UserService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public UserService(RestTemplate restTemplate, AuditRecordRepository auditRep) {
        this.restTemplate = restTemplate;
        this.auditRep = auditRep;
    }

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
        User user = super.create(incomingUser, USERS);
        return Objects.isNull(user) ? -1 : user.getId();
    }

    @CachePut(cacheNames = {"users"}, key = "#incomingUser.id")
    public int update(User incomingUser) {
        User user = super.update(incomingUser, incomingUser.getId(), USERS);
        if (Objects.isNull(user)) {
            logger.warn(String.format("User with id %d wasn't updated", incomingUser.getId()));
            return -1;
        } else {
            return user.getId();
        }
    }

    @CachePut(cacheNames = {"users_posts"}, key = "#id")
    public Optional<List<Post>> getPosts(int id) {
        logger.info(String.format("Get posts for user with id %d", id));
        Optional list = super.getList(id, String.format("%s/%d%s", USERS, id, ExternalApiConstants.POSTS));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Post>>() {}));

    }

    @CachePut(cacheNames = {"users_albums"}, key = "#id")
    public Optional<List<Album>> getAlbums(int id) {
        logger.info(String.format("Get albums for user with id %d", id));

        Optional<List<Album>> list = super.getList(id, String.format("%s/%d%s", USERS, id, ALBUMS));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Album>>() {}));
    }
}
