package ru.kduskov.vkapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kduskov.vkapi.model.external_api.Album;
import ru.kduskov.vkapi.service.AlbumService;

import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService){
        this.albumService = albumService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Album> getAlbum(@PathVariable Integer id) {
        Optional album = albumService.get(id);
        if(album.isPresent())
            return ResponseEntity.ok().body((Album) album.get());
        return ResponseEntity.notFound().build();
    }
}
