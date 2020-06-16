package com.revature.rms.auth.repositories;

import com.revature.rms.auth.entities.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    AppUser findAppUserById(int id);
    AppUser findAppUserByUsernameAndPassword(String username, String password);

}
