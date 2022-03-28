package ru.kata.spring.boot_security.demo.model;

import java.util.List;

public class RolesCreationDto {
    private List<Long> roles;

    public void addIdRoles(Long id) {
        this.roles.add(id);
    }
}
