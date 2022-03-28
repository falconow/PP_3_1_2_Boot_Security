package ru.kata.spring.boot_security.demo.service;




import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void add(User user);
    void delete(Long id);
    void update(User user);
    User findById(Long id);
    User findByLogin(String login);
    List<User> getAll();
    List<Role> getAllRoles();
    Role findRoleById(Long id);
}
