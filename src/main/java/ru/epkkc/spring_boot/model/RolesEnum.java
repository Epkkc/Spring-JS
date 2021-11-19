package ru.epkkc.spring_boot.model;

public enum RolesEnum {
    ADMIN, USER;

    @Override
    public String toString() {
        return this.name();
    }
}
