package ru.kduskov.vkapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.model.external.api.User;
import ru.kduskov.vkapi.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Optional user = userService.get(id);
        if(user.isPresent())
            return ResponseEntity.ok().body((User) user.get());
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Integer id) {
        logger.info(String.format("DELETE request for user with id %d", id));

        if(userService.get(id).isPresent())
            if(userService.delete(id))
                return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create( @RequestBody User user) {
        logger.info(String.format("POST request for user %s", user));
        return ResponseEntity.status(201).body(
                "{\"postId\":\"" + userService.create(user) + "\"}");
    }

    @PutMapping
    public ResponseEntity<String> put(@RequestParam Integer id, @RequestBody User incUser) {
        logger.info(String.format("PUT request for user with id %d and params for update %s", id, incUser));
        if(!userService.get(id).isPresent())
            return ResponseEntity.notFound().build();

        User user = (User) userService.get(id).get();
        user.setAddress(incUser.getAddress() == null ? user.getAddress() : incUser.getAddress());
        user.setName(incUser.getName() == null ? user.getName() : incUser.getName());
        user.setPhone(incUser.getPhone() == null ? user.getPhone() : incUser.getPhone());
        user.setCompany(incUser.getCompany() == null ? user.getCompany() : incUser.getCompany());
        user.setEmail(incUser.getEmail() == null ? user.getEmail() : incUser.getEmail());
        user.setWebsite(incUser.getWebsite() == null ? user.getWebsite() : incUser.getWebsite());
        return ResponseEntity.ok().body(
                "{\"postId\":\"" + userService.update(user) + "\"}");
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Integer id) {
        Optional user = userService.getPosts(id);
        if(user.isPresent())
            return ResponseEntity.ok().body((List<Post>) user.get());
        //TODO: return code
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}/albums", method = RequestMethod.GET)
    public ResponseEntity<List<Album>> getUserAlbums(@PathVariable Integer id) {
        Optional user = userService.getAlbums(id);
        if(user.isPresent())
            return ResponseEntity.ok().body((List<Album>) user.get());
        return ResponseEntity.notFound().build();
    }
}
