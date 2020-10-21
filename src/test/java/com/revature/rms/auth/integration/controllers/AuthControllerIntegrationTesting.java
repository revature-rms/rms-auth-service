package com.revature.rms.auth.integration.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rms.auth.AuthServiceApplication;
import com.revature.rms.auth.controllers.AuthController;
import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.services.AppUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
@AutoConfigureMockMvc
public class AuthControllerIntegrationTesting {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppUserService userService;

    Credentials credentials;
    AppUserDto appUserDto;
    ObjectMapper mapper;

    @Before
    public void setUp(){
        credentials = new Credentials("test", "test");
        appUserDto = new AppUserDto(1, "test", "test", null);
        mapper = new ObjectMapper();
    }

    /**
     * tests authenticate endpoint / post request using MockMVC testing and having userService return an app user when prompted
     * @throws Exception
     */
    @Test
    public void testAuthenticate() throws Exception{
        Mockito.when(userService.authenticate(credentials)).thenReturn(appUserDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(credentials))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test")));

    }
}
