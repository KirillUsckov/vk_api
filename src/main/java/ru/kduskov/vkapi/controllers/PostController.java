package ru.kduskov.vkapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kduskov.vkapi.model.external_api.Post;
import ru.kduskov.vkapi.service.PostService;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable Integer id) {
        Optional post = postService.get(id);
        if(post.isPresent())
            return (Post) post.get();
        //TODO: return code
        return null;
    }
}
