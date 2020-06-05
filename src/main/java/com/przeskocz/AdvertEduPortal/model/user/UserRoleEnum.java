package com.przeskocz.AdvertEduPortal.model.user;

public enum UserRoleEnum {
    NORMAL, ADMIN;

    public static UserRoleEnum fromValue(String value) {
        for(UserRoleEnum role : UserRoleEnum.values()) {
            if (role.toString().equals(value))
                return role;
        }
        return null;
    }
}