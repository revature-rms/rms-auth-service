package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.dtos.Principal;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private AppUserService userService;

    @Autowired
    public AppUserController(AppUserService service){

        this.userService = service;

    }

    @GetMapping
    public List<AppUser> getAllUsers(){

        return userService.getAllUsers();

    }

    @GetMapping(value = "/id/{id}")
    public AppUser getUserById(@PathVariable int id){

        return userService.getUserById(id);

    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal authenticate(@RequestBody Credentials creds){

        return userService.authenticate(creds);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser register(@RequestBody AppUser newUser){

        return userService.register(newUser);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser update(@RequestBody AppUser updatedUser){

        return userService.updateUser(updatedUser);

    }

}
