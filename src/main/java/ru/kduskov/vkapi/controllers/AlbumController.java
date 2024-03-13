package ru.kduskov.vkapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Photo;
import ru.kduskov.vkapi.service.AlbumService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService){
        this.albumService = albumService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Album> get(@PathVariable Integer id) {
        Optional album = albumService.get(id);
        if(album.isPresent())
            return ResponseEntity.ok().body((Album) album.get());
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Integer id) {
        logger.info(String.format("DELETE request for Album with id %d", id));
        if(albumService.get(id).isPresent())
            if(albumService.delete(id))
                return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> post( @RequestBody Album incAlbum) {
        logger.info(String.format("Album request for Album %s", incAlbum));

        return ResponseEntity.status(201).body(
                "{\"AlbumId\":\"" + albumService.create(incAlbum) + "\"}");
    }

    @PutMapping
    public ResponseEntity<String> put(@RequestParam Integer id, @RequestBody Album incAlbum) {
        logger.info(String.format("PUT request for Album with id %d and params for update %s", id, incAlbum));
        if(albumService.get(id).isEmpty())
            return ResponseEntity.notFound().build();

        Album album = (Album) albumService.get(id).get();
        album.setUserId(incAlbum.getUserId() == 0 ? album.getUserId() : incAlbum.getUserId());
        album.setTitle(incAlbum.getTitle() == null ? album.getTitle() : incAlbum.getTitle());
        return ResponseEntity.ok().body(
                "{\"AlbumId\":\"" + albumService.update(album) + "\"}");
    }

    @RequestMapping(value = "/{id}/photos", method = RequestMethod.GET)
    public ResponseEntity<List<Photo>> getAlbumsPhotos(@PathVariable Integer id) {
        logger.info(String.format("GET request for album with id %d", id));
        if(albumService.get(id).isEmpty())
            return ResponseEntity.notFound().build();

        Optional photoList = albumService.getPhotos(id);
        if (photoList.isPresent())
            return ResponseEntity.ok().body((List<Photo>)photoList.get());
        return ResponseEntity.notFound().build();
    }
}
