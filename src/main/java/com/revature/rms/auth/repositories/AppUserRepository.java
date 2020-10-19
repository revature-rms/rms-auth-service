package com.revature.rms.auth.repositories;

import com.revature.rms.auth.entities.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * findAppUserById method: The id parameter is passed as the input.
     * A user is returned when the input id matches a database record.
     * @param id user id int
     * @return user with matching id int
     */
    AppUser findAppUserById(int id);

    /**
     * findAppUserByUsernameAndPassword: The username and password parameters are passed as the input.
     * An appUser is returned when the input username and password Strings match database records.
     * @param username appUser username cred
     * @param password appUser password cred
     * @return appUser with matching username and password Strings
     */
    AppUser findAppUserByUsernameAndPassword(String username, String password);

    /**
     * findAppUserByUsername: The username parameter is passed as the input.
     * An appUser is returned when the input username String match database records.
     * @param username appUser username cred
     * @return appUser with matching username and password Strings
     */
    AppUser findAppUserByUsername(String username);

    /**
     * findAppUserByEmail: The email parameter is passed as the input.
     * An appUser is returned when the input email String match database records.
     * @param email appUser username cred
     * @return appUser with matching username and password Strings
     */
    AppUser findAppUserByEmail(String email);

}
