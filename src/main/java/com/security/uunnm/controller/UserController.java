package com.security.uunnm.controller;

import com.security.uunnm.entity.Users;
import com.security.uunnm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Users register(@RequestBody Users user) {
        return userService.save(user);
    }

    @PostMapping("login")
    public String login(@RequestBody Users user) {
        return userService.login(user);
    }
}
