package com.revature.rms.auth.controllers;

import com.revature.rms.auth.services.AppUserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AppUserService userService;

    @InjectMocks
    private AuthController authController;

    public void init(){
        MockitoAnnotations.initMocks(this);
    }

}
