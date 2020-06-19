package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
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

    /**
     * authenticate method: Takes in a Credentials object as the input.
     * @param creds Credentials object containing appUser username and password Strings
     * @return the creds object of the authenticated app User
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto authenticate(@RequestBody Credentials creds){

        return userService.authenticate(creds);

    }

}
