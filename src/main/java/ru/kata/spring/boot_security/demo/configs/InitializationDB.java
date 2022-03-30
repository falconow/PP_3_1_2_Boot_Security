package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class InitializationDB {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitializationDB(UserService userService, RoleService roleService, RoleService roleService1) {
        this.userService = userService;
        this.roleService = roleService1;
    }


    @PostConstruct
    public void createDefaultUserAndRoles() {
        //Создание ролей
        String[] roleNames = {"ROLE_ADMIN","ROLE_USER"};
        List<Role> listRoles = new ArrayList<>();
        for (String name:roleNames) {
            Role role = new Role();
            role.setRole(name);
            listRoles.add(role);
        }

        //Создание пользователя
        User defaultUser = new User();
        defaultUser.setFirstName("Admin");
        defaultUser.setLastName("Admin");
        defaultUser.setEmail("admin@kata.info");
        defaultUser.setLogin("admin");
        defaultUser.setPassword("admin");
        defaultUser.setPhone("+79999999999");
        defaultUser.setCollectionsRoles(listRoles);
        userService.addUser(defaultUser);

    }


}
