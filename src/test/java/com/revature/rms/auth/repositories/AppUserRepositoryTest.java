package com.revature.rms.auth.repositories;

import com.revature.rms.auth.AuthServiceApplication;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void getAllTest(){

        Iterable<AppUser> result = appUserRepository.findAll();

        assertNotNull(result);

    }

    @Test
    public void getByUsernameAndPasswordTest(){

        AppUser result = appUserRepository.findAppUserByUsernameAndPassword("wsingleton", "password");

        assertEquals(result.getId(), 1);

    }

    @Test
    public void getByIdTest(){

        AppUser result = appUserRepository.findAppUserById(1);

        assertEquals(result.getUsername(), "wsingleton");

    }

}
