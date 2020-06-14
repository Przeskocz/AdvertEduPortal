package com.przeskocz.AdvertEduPortal.model;

public enum UserRoleEnum {
    USER, ADMIN;

    public static UserRoleEnum fromString(String value) {
        for(UserRoleEnum role : UserRoleEnum.values()) {
            if (role.toString().equalsIgnoreCase(value))
                return role;
        }
        return USER;
    }
}