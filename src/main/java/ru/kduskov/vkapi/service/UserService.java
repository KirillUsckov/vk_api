package ru.kduskov.vkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    public Optional<User> get(int id){
        Optional user = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", ExternalApiConstants.USERS, id);
            user = Optional.ofNullable(restTemplate.getForObject(endpoint, User.class));
        }
        catch (Exception e) {
        }
        return user;
    }

    public Optional<Post> getPosts(int id){
        Optional postsList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", ExternalApiConstants.USERS, id, ExternalApiConstants.POSTS);

            ResponseEntity<List<Post>> rateResponse =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
                            });
            System.out.println("LIST: " + rateResponse.getBody().stream().toString());
            postsList = Optional.of(rateResponse.getBody());
        }
        catch (Exception e) {
        }
        return postsList;
    }

    public Optional<List<Album>> getAlbums(int id){
        Optional albumsList = Optional.empty();
        try {
            String endpoint = String.format("%s/%d%s", ExternalApiConstants.USERS, id, ExternalApiConstants.ALBUMS);

            ResponseEntity<List<Album>> rateResponse =
                    restTemplate.exchange(endpoint,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Album>>() {
                            });
            albumsList = Optional.of(rateResponse.getBody());

            //albumsList = Optional.ofNullable(restTemplate.getForObject(endpoint, List<Album>.class));
        }
        catch (Exception e) {
        }
        return albumsList;
    }
}
