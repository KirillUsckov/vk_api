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
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Photo;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;
import ru.kduskov.vkapi.repository.AuditRecordRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class AlbumService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    @Autowired
    public AlbumService(RestTemplate restTemplate, AuditRecordRepository auditRep) {
        this.restTemplate = restTemplate;
        this.auditRep = auditRep;
    }

    @Cacheable(cacheNames = {"albums"}, key = "#id")
    public Optional<Album> get(int id) {
        return super.get(id, ALBUMS, Album.class);
    }

    @CacheEvict(cacheNames = {"albums"}, key = "#id")
    public boolean delete(int id) {
        return super.delete(id, ALBUMS);
    }

    @Cacheable(cacheNames = {"albums"}, key = "#incomingAlbum.id")
    public int create(Album incomingAlbum) {
        Album album = super.create(incomingAlbum, ALBUMS);
        return Objects.isNull(album) ? -1 : album.getId();
    }

    @CachePut(cacheNames = {"albums"}, key = "#incomingAlbum.id")
    public int update(Album incomingAlbum) {
        Album album = super.update(incomingAlbum, incomingAlbum.getId(), ALBUMS);
        if (Objects.isNull(album)) {
            logger.warn(String.format("Album with id %d wasn't updated", incomingAlbum.getId()));
            return -1;
        } else {
            return album.getId();
        }
    }

    @CachePut(cacheNames = {"albums_photos"}, key = "#id")
    public Optional<List<Photo>> getPhotos(int id) {
        logger.info(format("Get photos for album with id %d", id));
        Optional list = super.getList(id, format("%s/%d%s", ALBUMS, id, PHOTOS));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Photo>>() {}));
    }

    @CachePut(cacheNames = {"users_albums"}, key = "#userId")
    public Optional<List<Album>> getAlbumsByUser(Integer userId) {
        Optional list =  super.getList(userId, String.format("%s?userId=%d", ALBUMS, userId));
        return Optional.of(new ObjectMapper().convertValue(list.get(), new TypeReference<List<Album>>() {}));
    }
}
