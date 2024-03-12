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
import ru.kduskov.vkapi.model.external_api.Comment;
import ru.kduskov.vkapi.model.external_api.Photo;
import ru.kduskov.vkapi.model.external_api.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.kduskov.vkapi.constants.ExternalApiConstants.*;

@Service
public class AlbumService extends BaseService {
    private final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    @Cacheable(cacheNames = {"albums"}, key = "#id")
    public Optional<Album> get(int id){
        System.out.println("get album with id : "+ id);
        Optional album = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", ExternalApiConstants.ALBUMS, id);
            logger.info("Send POST request to :" + URL + endpoint);

            album = Optional.ofNullable(restTemplate.getForObject(endpoint, Album.class));
            logger.info("ALBUM: " + album.toString());
        }
        catch (Exception e) {
            logger.warn(String.format("Album with id %d wasn't found:$s", id, e.getMessage()));
        }
        return album;
    }

    @CacheEvict(cacheNames = {"albums"}, key = "#id")
    public boolean delete(int id) {
//        logger.info(String.format("Delete album with id %d", id));
//        try {
//            String endpoint = String.format("%s/%d", ALBUMS, id);
//            logger.info("Send DELETE request to :" + URL + endpoint);
//
//            restTemplate.delete(endpoint);
//        } catch (Exception e) {
//            logger.error(String.format("Error while deleting post with id %d: %s", id, e.getMessage()));
//            return false;
//        }
//        return true;
        return super.delete(id, "album", ALBUMS);
    }

    @Cacheable(cacheNames = {"albums"}, key = "#incomingAlbum.id")
    public int create(Album incomingAlbum) {
        logger.info(String.format("Create album with id %d", incomingAlbum.getId()));

        HttpEntity<Album> request = new HttpEntity<>(incomingAlbum);
        logger.info("Send POST request to :" + URL + ALBUMS);
        Album album = restTemplate.postForObject(ALBUMS, request, Album.class);

        return album.getId();
    }

    @CachePut(cacheNames = {"albums"}, key = "#incomingAlbum.id")
    public int update(Album incomingAlbum) {
        logger.info(String.format("Update album with id %d", incomingAlbum.getId()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String endpoint = String.join("/",ALBUMS, String.valueOf(incomingAlbum.getId()));
        logger.info("Send PUT request to :" + URL + endpoint);
        ResponseEntity<Album> response = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                new HttpEntity<Album>(incomingAlbum),
                Album.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getId();
        } else {
            logger.warn(String.format("Album with id %d wasn't updated", incomingAlbum.getId()));
            return -1;
        }
    }

    @CachePut(cacheNames = {"albums_photos"}, key = "#id")
    public Optional<List<Photo>> getPhotos(int id) {
        logger.info(String.format("Get photos for album with id %d", id));

        Optional photosList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", ALBUMS, id, PHOTOS);
            logger.info("Send GET request to :" + URL + endpoint);
            ResponseEntity<List<Photo>> response =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Photo>>() {
                            });
            photosList = Optional.of(response.getBody());
        }
        catch (Exception e) {
            logger.warn(String.format("Comments for post with id %d wasn't found cause of exception %s", id, e.getMessage()));
        }
        return photosList;
    }
}
