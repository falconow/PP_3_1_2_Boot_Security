package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RolesCreationDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Controller
public class UserController {
    private final UserService userService;
    private Logger logger = Logger.getLogger(UserController.class.getName());


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String printIndex() {
        return "index";
    }

    @GetMapping("/admin")
    public String printAdmin(Model model) {
        List<User> listUsers = userService.getAll();
        model.addAttribute("listUsers", listUsers);
        return "adminPanel";
    }

    //Форма для ввода нового пользователя
    @GetMapping(value = "/admin/new")
    public String printFormCreate(Model model) {
        RolesCreationDto rolesSelected = new RolesCreationDto();
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("selectRoles", new ArrayList<>());
        return "createUserForm";
    }

    //Форма для редактирования
    @GetMapping(value = "/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User editUser = userService.findById(id);
        model.addAttribute("user", editUser);
        return "editUserForm";
    }

    //Сохранение нового пользователя
    @PostMapping(value = "/admin/new")
    public String createUser(@ModelAttribute User user, @RequestParam("roles") List<Long> selectRoles) {
        //logger.info(selectRoles.toString());
        List<Role> roles = selectRoles.stream().map(userService::findRoleById).toList();
        user.setCollectionsRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    //Удаление пользователя
    @DeleteMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    //Изменение пользователя
    @PutMapping(value = "/admin/{id}/edit")
    public String saveEditUser(Model model, @ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    //Страница user
    @GetMapping("/user")
    public String printUser(Principal principal, Model model) {
        User user = userService.findByLogin(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }



}
