package com.revature.rms.auth.controllers;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;
import com.revature.rms.auth.services.AppUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AppUserController.class)
public class AppUserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppUserService userService;

    @Autowired
    private void setMockMvc(MockMvc mock){

        this.mockMvc = mock;

    }

    @Before
    public void setup(){

        final AppUserController appUserController = new AppUserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();

    }

    //****************************************GET ALL TESTS********************************************************************



    //****************************************GET BY ID TESTS********************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenCorrectId() throws Exception{

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        AppUserDto mockDto = new AppUserDto(mockUser);

        when(userService.getUserById(1)).thenReturn(mockDto);

        this.mockMvc.perform(get("/users/id/1"))
                .andExpect(status().isOk());

    }

}
