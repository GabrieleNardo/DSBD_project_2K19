package com.dslab.video_management_service.controller;

import com.dslab.video_management_service.entity.User;
import com.dslab.video_management_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    UserService user_service;

    @GetMapping(path="")
    public @ResponseBody Iterable<User> views(){
        return user_service.getUsers();
    }

    // POST http://localhost:8080/register
    @PostMapping(path = "/register")
    public @ResponseBody User register(@RequestBody User user) {
        user.setRoles("USER");
        return user_service.addUser(user);
    }


}
