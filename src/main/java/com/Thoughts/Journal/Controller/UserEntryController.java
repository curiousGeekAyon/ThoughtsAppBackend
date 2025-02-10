package com.Thoughts.Journal.Controller;

import com.Thoughts.Journal.Entity.User;
import com.Thoughts.Journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    UserService userService;

    @PutMapping
    public User updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.updateUser(authentication.getName(), user);
    }

    @DeleteMapping("/remove")
    public User remove() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userService.removeUser(userName);
    }

}
