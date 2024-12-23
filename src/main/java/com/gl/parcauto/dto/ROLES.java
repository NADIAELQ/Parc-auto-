package com.gl.parcauto.dto;

public enum ROLES {
    ROLE_ADMIN("ADMIN"), ROLE_DRIVER("DRIVER"), ROLE_MANAGER_TRIP("MANAGER_TRIP");
    final String shortName;
    ROLES(String shortName) {
        this.shortName = shortName;
    }
    public String  getShortName() {
        return shortName;
    }
}
