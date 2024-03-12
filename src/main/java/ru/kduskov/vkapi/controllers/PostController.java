package ru.kduskov.vkapi.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kduskov.vkapi.model.external_api.Comment;
import ru.kduskov.vkapi.model.external_api.Post;
import ru.kduskov.vkapi.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable Integer id) {
        logger.info(String.format("GET request for post with id %d", id));

        Optional post = postService.get(id);
        if (post.isPresent())
            return ResponseEntity.ok().body((Post)post.get());
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable Integer id) {
        logger.info(String.format("DELETE request for post with id %d", id));

        if(postService.get(id).isPresent())
            if(postService.delete(id))
                return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> post( @RequestBody Post incPost) {
        logger.info(String.format("POST request for post %s", incPost));
        return ResponseEntity.status(201).body(
                "{\"postId\":\"" + postService.create(incPost) + "\"}");
    }

    @PutMapping
    public ResponseEntity<String> put(@RequestParam Integer id, @RequestBody Post incPost) {
        logger.info(String.format("PUT request for post with id %d and params for update %s", id, incPost));
        if(!postService.get(id).isPresent())
            return ResponseEntity.notFound().build();

        Post post = (Post) postService.get(id).get();
        post.setUserId(incPost.getUserId() == 0 ? post.getUserId() : incPost.getUserId());
        post.setTitle(incPost.getTitle() == null ? post.getTitle() : incPost.getTitle());
        post.setBody(incPost.getBody() == null ? post.getBody() : incPost.getBody());
        return ResponseEntity.ok().body(
                "{\"postId\":\"" + postService.update(post) + "\"}");
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getPostsComments(@PathVariable Integer id) {
        logger.info(String.format("GET request for post with id %d", id));
        if(!postService.get(id).isPresent())
            return ResponseEntity.notFound().build();

        Optional post = postService.getComments(id);
        if (post.isPresent())
            return ResponseEntity.ok().body((List<Comment>)post.get());
        return ResponseEntity.notFound().build();
    }
}
