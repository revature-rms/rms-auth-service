package com.revature.rms.auth.dtos;

import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;

import java.util.Objects;

public class Principal {

    private int id;
    private String username;
    private UserRole role;

    public Principal() {
    }

    public Principal(int id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Principal(AppUser user){

        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return id == principal.id &&
                Objects.equals(username, principal.username) &&
                role == principal.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

}
