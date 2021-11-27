package ru.epkkc.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.User;
import ru.epkkc.spring_boot.services.RoleServiceInt;
import ru.epkkc.spring_boot.services.UserServiceInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private final UserServiceInt userService;
    private final RoleServiceInt roleService;

    @Autowired
    public AppController(UserServiceInt userService, RoleServiceInt roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String usersTable(ModelMap model) {
        List<User> allUsers = userService.findAll();
        for (User user : allUsers) {
            System.out.println(user);
        }
        List<Role> roles = roleService.findAll();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        model.addAttribute("users_list", allUsers);
        model.addAttribute("user_add", user);
        model.addAttribute("user_update", user);
        model.addAttribute("allRoles", roles);
        model.addAttribute("current_user", currentUser);
        return "admin_page_js";
    }

    @PostMapping("/post_admin")
    public String addUser(@ModelAttribute(name = "user_add") User user) {
        System.out.println("\nADDING NEW USER\n" + user);
        userService.save(roleService.findRolesInDB(user));
        return "redirect:/admin";
    }

    @PutMapping("/put_admin")
    public String patchUser(@ModelAttribute(name = "user_update") User user) {
        System.out.println("\nEDIT\n" + user);
        userService.update(roleService.findRolesInDB(user));
        return "redirect:/admin";
    }

    @DeleteMapping("/delete_admin")
    public String removeUser(@RequestParam(name = "user_id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage(ModelMap model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("current_user", currentUser);
        return "user_page_bootstrap";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

}
