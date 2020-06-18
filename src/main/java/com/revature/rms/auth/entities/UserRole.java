package com.revature.rms.auth.entities;

public enum UserRole {

    TRAINING_SITE_MANAGER("Training Site Manager"), BUILDING_MANAGER("Building Manager"), TRAINER("Trainer"), ADMIN("Admin"), LOCKED("Locked");

    private String roleName;

    UserRole(String name) {
        this.roleName = name;
    }

    public static UserRole getByName(String name) {
        for (UserRole role : UserRole.values()) {
            if (role.roleName.equals(name)) {
                return role;
            }
        }
        return LOCKED;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

}
