package com.revature.rms.auth.entities;

import com.revature.rms.auth.dtos.RegisterDto;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AppUser implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false,unique=true)
    private String email;

    @Column(nullable=false,unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<UserRole> role;

    public AppUser() {
        super();
    }

    public AppUser(String email, String username, String password, List<UserRole> role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AppUser(int id, String email, String username, String password, List<UserRole> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AppUser(RegisterDto newUser){
        this.id = newUser.getId();
        this.email = newUser.getEmail();
        this.username = newUser.getUsername();
        this.password = newUser.getPassword();

        List<UserRole> roles = new ArrayList<UserRole>();

        for(String role:newUser.getRole()){
            roles.add(UserRole.getByName(role));
        }

        this.role = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRole() {
        return role;
    }

    public void setRole(List<UserRole> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password) &&
                role == appUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, role);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

}
