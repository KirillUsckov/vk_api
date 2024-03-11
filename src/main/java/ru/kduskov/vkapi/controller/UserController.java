package ru.kduskov.vkapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kduskov.vkapi.model.User;
import ru.kduskov.vkapi.service.UserService;

import java.util.Optional;
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public User getUser(@RequestParam("id") Integer id) {
        Optional user = userService.getUser(id);
        if(user.isPresent())
            return (User) user.get();
        //TODO: return code
        return null;
    }
}
