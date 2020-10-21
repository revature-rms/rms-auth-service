package com.revature.rms.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rms.auth.AuthServiceApplication;
import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.RegisterDto;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;

import com.revature.rms.auth.services.AppUserService;
import com.revature.rms.core.exceptions.InvalidRequestException;
import com.revature.rms.core.exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = AppUserController.class)
@SpringBootTest(classes = AuthServiceApplication.class)
@AutoConfigureMockMvc
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService userService;

    ObjectMapper mapper = new ObjectMapper();

    //****************************************GET ALL TESTS********************************************************************

    @Test
    public void shouldReturnListOfAllAppUsers() throws Exception{

        //Arrange
        List<String> mockRoles = new ArrayList<String>();
        mockRoles.add("Admin");
        mockRoles.add("Training Site Manager");
        List<AppUserDto> mockDtos = new ArrayList<AppUserDto>();
        mockDtos.add(new AppUserDto(1, "test1@revature.com", "test1", mockRoles));
        mockDtos.add(new AppUserDto(2, "test2@revature.com", "test2", mockRoles));
        mockDtos.add(new AppUserDto(3, "test3@revature.com", "test3", mockRoles));

        when(userService.getAllUsers()).thenReturn(mockDtos);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenDatabaseIsEmpty() throws Exception{

        //Arrange
        when(userService.getAllUsers()).thenThrow(ResourceNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    //****************************************GET BY ID TESTS********************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenCorrectId() throws Exception{

        //Arrange
        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        AppUserDto mockDto = new AppUserDto(mockUser);

        when(userService.getUserById(1)).thenReturn(mockDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/id/1").accept(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.username", is("test1")));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenIdIsLessThanOne() throws Exception{

        //Arrange
        when(userService.getUserById(0)).thenThrow(InvalidRequestException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/id/0").accept(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenIdIsNotFound() throws Exception{

        //Arrange
        when(userService.getUserById(100)).thenThrow(ResourceNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/id/100").accept(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    //****************************************REGISTER TESTS********************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenRegisteringAUser() throws Exception{

        //Arrange
        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        AppUserDto mockDto = new AppUserDto(mockUser);
        List<String> mockStringRoles = new ArrayList<String>();
        mockStringRoles.add("Admin");
        mockStringRoles.add("Training Site Manager");

        RegisterDto mockReqBody = new RegisterDto("test1", "test1@revature.com", "password", mockStringRoles);

        when(userService.register(any(RegisterDto.class))).thenReturn(mockDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockReqBody))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.username", is("test1")));

    }

    @Test
    public void shouldThrowBadRequestErrorWhenGivenInvalidUserObj() throws Exception{

        //Arrange
        List<String> mockStringRoles = new ArrayList<String>();
        mockStringRoles.add("Admin");
        mockStringRoles.add("Training Site Manager");

        RegisterDto mockReqBody = new RegisterDto(null, "test1@revature.com", "password", mockStringRoles);

        when(userService.register(any(RegisterDto.class))).thenThrow(InvalidRequestException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockReqBody))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

    }

    //****************************************UPDATE TESTS********************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenUpdatingAUser() throws Exception{

        //Arrange
        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        AppUserDto mockDto = new AppUserDto(mockUser);
        List<String> mockStringRoles = new ArrayList<String>();
        mockStringRoles.add("Admin");
        mockStringRoles.add("Training Site Manager");

        RegisterDto mockReqBody = new RegisterDto(1,"test1", "test1@revature.com", "password", mockStringRoles);

        when(userService.updateUser(any(RegisterDto.class))).thenReturn(mockDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockReqBody))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.username", is("test1")));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidUserToUpdate() throws Exception{

        //Arrange
        List<String> mockStringRoles = new ArrayList<String>();
        mockStringRoles.add("Admin");
        mockStringRoles.add("Training Site Manager");

        RegisterDto mockReqBody = new RegisterDto(1,null, "test1@revature.com", "password", mockStringRoles);

        when(userService.updateUser(any(RegisterDto.class))).thenThrow(InvalidRequestException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockReqBody))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenNoUserFoundToUpdate() throws Exception{

        //Arrange
        List<String> mockStringRoles = new ArrayList<String>();
        mockStringRoles.add("Admin");
        mockStringRoles.add("Training Site Manager");

        RegisterDto mockReqBody = new RegisterDto(100,"test1", "test1@revature.com", "password", mockStringRoles);

        when(userService.updateUser(any(RegisterDto.class))).thenThrow(ResourceNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockReqBody))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

}
