package ru.kduskov.vkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external_api.Album;

import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private RestTemplate restTemplate;

    public Optional<Album> get(int id){
        Optional album = Optional.empty();
        try {
            String endpoint = String.format("%s/%d", ExternalApiConstants.ALBUMS, id);
            album = Optional.ofNullable(restTemplate.getForObject(endpoint, Album.class));
        }
        catch (Exception e) {
        }
        return album;
    }
}
