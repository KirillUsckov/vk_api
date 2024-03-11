package ru.kduskov.vkapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kduskov.vkapi.model.User;

import java.util.Optional;

public class PostController {

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String getPosts() {

        //TODO: return code
        return "post";
    }
}
