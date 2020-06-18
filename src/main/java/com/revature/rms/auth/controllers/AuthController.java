package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    private AppUserService userService;

    @Autowired
    public AuthController(AppUserService service){

        this.userService = service;

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser authenticate(@RequestBody Credentials creds){

        return userService.authenticate(creds);

    }

}
