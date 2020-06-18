package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.dtos.Principal;
import com.revature.rms.auth.dtos.RegisterDto;
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
    public List<AppUserDto> getAllUsers(){

        return userService.getAllUsers();

    }

    @GetMapping(value = "/id/{id}")
    public AppUserDto getUserById(@PathVariable int id){

        return userService.getUserById(id);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto register(@RequestBody RegisterDto newUser){

        return userService.register(newUser);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto update(@RequestBody RegisterDto updatedUser){

        return userService.updateUser(updatedUser);

    }

}
