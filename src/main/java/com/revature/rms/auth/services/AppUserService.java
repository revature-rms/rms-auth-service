package com.revature.rms.auth.services;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.dtos.RegisterDto;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.exceptions.AuthenticationException;
import com.revature.rms.auth.exceptions.BadRequestException;
import com.revature.rms.auth.exceptions.ResourceNotFoundException;
import com.revature.rms.auth.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {

    private AppUserRepository userRepository;

    @Autowired
    public AppUserService(AppUserRepository repo){

        this.userRepository = repo;

    }

    @Transactional(readOnly = true)
    public List<AppUserDto> getAllUsers(){

        Iterable<AppUser> iterable = userRepository.findAll();

        List<AppUser> users = getListFromIterator(iterable);

        if(users.isEmpty()){
            throw new ResourceNotFoundException();
        }

        List<AppUserDto> userDtos = new ArrayList<>();

        for(AppUser user : users){
            userDtos.add(new AppUserDto(user));
        }

        return userDtos;

    }

    @Transactional(readOnly = true)
    public AppUserDto getUserById(int id){

        if(id <= 0){
           throw new BadRequestException();
        }

        AppUser retrievedUser = userRepository.findAppUserById(id);

        if(retrievedUser == null){
            throw new ResourceNotFoundException();
        }

        return new AppUserDto(retrievedUser);

    }

    @Transactional(readOnly = true)
    public AppUserDto authenticate(Credentials creds){

        if(creds.getUsername() == null || creds.getUsername().trim().equals("") || creds.getPassword() == null || creds.getPassword().trim().equals("")){
            throw new BadRequestException();
        }

        AppUser retrievedUser = userRepository.findAppUserByUsernameAndPassword(creds.getUsername(), creds.getPassword());

        //NEED TO TRY THIS OUT, MIGHT NEED A TRY/CATCH
        if(retrievedUser == null){
            throw new AuthenticationException();
        }

        return new AppUserDto(retrievedUser);

    }

    @Transactional
    public AppUserDto register(RegisterDto newUser){

        if(
                newUser.getUsername() == null || newUser.getUsername().trim().equals("") ||
                newUser.getPassword() == null || newUser.getPassword().trim().equals("") ||
                newUser.getEmail() == null || newUser.getEmail().trim().equals("")
        ){
            throw new BadRequestException();
        }

        AppUser user = new AppUser(newUser);

        return new AppUserDto(userRepository.save(user));

    }

    @Transactional
    public AppUserDto updateUser(RegisterDto updatedUser){

        if(
                updatedUser.getUsername() == null || updatedUser.getUsername().trim().equals("") ||
                updatedUser.getPassword() == null || updatedUser.getPassword().trim().equals("") ||
                updatedUser.getEmail() == null || updatedUser.getEmail().trim().equals("") ||
                updatedUser.getId() <= 0
        ){
            throw new BadRequestException();
        }

        AppUser persistedUser = userRepository.findAppUserById(updatedUser.getId());

        if(persistedUser == null){
            throw new ResourceNotFoundException();
        }

        AppUser user = new AppUser(updatedUser);

        return new AppUserDto(userRepository.save(user));

    }

    public static <T> List<T> getListFromIterator(Iterable<T> iterable) {

        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;

    }

}
