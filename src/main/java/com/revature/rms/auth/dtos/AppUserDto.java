package com.revature.rms.auth.dtos;


import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppUserDto {

    private int id;
    private String email;
    private String username;
    private List<String> role;

    public AppUserDto() {}

    public AppUserDto(int id, String email, String username, String password, List<String> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public AppUserDto(int id, String email, String username, List<String> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public AppUserDto(AppUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();

        List<String> roles = new ArrayList<String>();

        for(UserRole role:user.getRole()){
            roles.add(role.toString());
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserDto that = (AppUserDto) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, role);
    }

    @Override
    public String toString() {
        return "AppUserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
