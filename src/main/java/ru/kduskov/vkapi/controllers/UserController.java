package ru.kduskov.vkapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kduskov.vkapi.model.external_api.Album;
import ru.kduskov.vkapi.model.external_api.Post;
import ru.kduskov.vkapi.model.external_api.User;
import ru.kduskov.vkapi.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Integer id) {
        Optional user = userService.get(id);
        if(user.isPresent())
            return (User) user.get();
        //TODO: return code
        return null;
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public List<Post> getUserPosts(@PathVariable Integer id) {
        Optional user = userService.getPosts(id);
        if(user.isPresent())
            return (List<Post>) user.get();
        //TODO: return code
        return null;
    }

    @RequestMapping(value = "/{id}/albums", method = RequestMethod.GET)
    public List<Album> getUserAlbums(@PathVariable Integer id) {
        Optional user = userService.getAlbums(id);
        if(user.isPresent())
            return (List<Album>) user.get();
        //TODO: return code
        return null;
    }
}
