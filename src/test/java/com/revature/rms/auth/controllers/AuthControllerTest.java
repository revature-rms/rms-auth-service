package com.revature.rms.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rms.auth.AuthServiceApplication;
import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;
import com.revature.rms.auth.services.AppUserService;
import com.revature.rms.core.exceptions.AuthenticationException;
import com.revature.rms.core.exceptions.InvalidRequestException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = AuthController.class)
@SpringBootTest(classes = AuthServiceApplication.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnAppUserDtoWhenGivenValidCredentials() throws Exception{

        //Arrange
        Credentials mockCreds = new Credentials("test1", "password");

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        AppUserDto mockDto = new AppUserDto(mockUser);

        when(userService.authenticate(mockCreds)).thenReturn(mockDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockCreds))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.username", is("test1")));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredentials() throws Exception{

        //Arrange
        Credentials mockCreds = new Credentials(null, "password");

        when(userService.authenticate(any(Credentials.class))).thenThrow(InvalidRequestException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockCreds))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

    }

    @Test
    public void shouldThrowAuthenticationExceptionWhenGivenCredsThatDontExist() throws Exception{

        //Arrange
        Credentials mockCreds = new Credentials("test1", "password");

        when(userService.authenticate(any(Credentials.class))).thenThrow(AuthenticationException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockCreds))
                .contentType(MediaType.APPLICATION_JSON);

        //Act/Assert
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());

    }

}
