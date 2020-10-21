package com.revature.rms.auth.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rms.auth.AuthServiceApplication;
import com.revature.rms.auth.controllers.AppUserController;
import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.RegisterDto;
import com.revature.rms.auth.services.AppUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTesting {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppUserService userService;

    AppUserDto appUserDto;
    List<AppUserDto> appUserDtoList;
    RegisterDto registerDto;
    ObjectMapper mapper;

    @Before
    public void setUp(){
        appUserDto = new AppUserDto(1, "test", "test", null);
        appUserDtoList = Arrays.asList(appUserDto);
        registerDto = new RegisterDto(2, "test", "test", "test", null);
        mapper = new ObjectMapper();
    }

    /**
     * tests get all users endpoint /users get request using MockMVC testing and having userService return a list of app users when prompted
     * @throws Exception
     */
    @Test
    public void testGetAllUsers() throws Exception{
        Mockito.when(userService.getAllUsers()).thenReturn(appUserDtoList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("test")));
    }

    /**
     * tests get user by id endpoint /users/id/{id} using MockMVC testing and having userService return an app user when prompted
     * @throws Exception
     */
    @Test
    public void testGetUserById() throws Exception{
        Mockito.when(userService.getUserById(1)).thenReturn(appUserDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/id/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test")));
    }

    /**
     * tests register endpoint /users post request using MockMVC testing and having userService return an app user when prompted
     * @throws Exception
     */
    @Test
    public void testRegister() throws Exception{
        Mockito.when(userService.register(registerDto)).thenReturn(appUserDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerDto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test")));
    }

    /**
     * tests update endpoint /users put request using MockMVC testing and having userService return an app user when prompted
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception{
        Mockito.when(userService.updateUser(registerDto)).thenReturn(appUserDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerDto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test")));
    }
}
