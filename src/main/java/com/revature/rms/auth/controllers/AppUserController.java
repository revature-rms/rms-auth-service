package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.RegisterDto;
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
    public AppUserController(AppUserService service){this.userService = service;}

    /**
     * getAllUsersReturns a list of all the user objects in the database.
     * @return a list of all the users
     */
    @GetMapping
    public List<AppUserDto> getAllUsers(){return userService.getAllUsers();}

    /**
     * getUserById method: Returns an user object when the id int matches a record in the database.
     * @param id userId int value
     * @return an user with matching id
     */
    @GetMapping(value = "/id/{id}")
    public AppUserDto getUserById(@PathVariable int id){return userService.getUserById(id);}

    /**
     * register method: Takes in a RegisterDto object as the input.
     * @param newUser AppUserDto object
     * @return the newly added user object
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto register(@RequestBody RegisterDto newUser){return userService.register(newUser);}

    /**
     * update method: The user object is inputted and changes are saved.
     * @param updatedUser newly updated user object
     * @return updated/modified user object
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto update(@RequestBody RegisterDto updatedUser){return userService.updateUser(updatedUser);}

}
