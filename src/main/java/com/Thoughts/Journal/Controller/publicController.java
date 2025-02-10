package com.Thoughts.Journal.Controller;

import com.Thoughts.Journal.Entity.User;
import com.Thoughts.Journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class publicController {
    @Autowired
    UserService userService;
    @GetMapping
    public String healthCheck()
        {
            return "OK";
        }

    @GetMapping("/getall")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        if (addedUser != null) {
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
