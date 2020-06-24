package com.revature.rms.auth.repositories;

import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    public void getAllTest(){
        List<UserRole> roleList= new ArrayList<>();
        roleList.add(UserRole.TRAINER);
        AppUser user = new AppUser();
        user.setId(1);
        user.setUsername("TestUser");
        user.setEmail("test@user.com");
        user.setPassword("test");
        user.setRole(roleList);

        appUserRepository.findById(1);

        Assert.assertEquals(user.getUsername(),appUserRepository.findAppUserById(1).getUsername());
    }

}
