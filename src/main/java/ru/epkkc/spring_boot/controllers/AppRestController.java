package ru.epkkc.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.epkkc.spring_boot.model.User;
import ru.epkkc.spring_boot.services.RoleServiceInt;
import ru.epkkc.spring_boot.services.UserServiceInt;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppRestController {

    private final UserServiceInt userService;
    private final RoleServiceInt roleService;

    @Autowired
    public AppRestController(UserServiceInt userService, RoleServiceInt roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserWithId(@PathVariable long id) {
        return userService.findById(id);
    }

    @GetMapping("/current-user")
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        System.out.println("\nAPI ADD USER: " + user + "\n");
        userService.save(roleService.findRolesInDB(user));
        return userService.findByUsername(user.getUsername());
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        System.out.println("\nAPI UPDATE USER: " + user + "\n");
        userService.update(user);
        return userService.findById(user.getId());
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        System.out.println("\nAPI DELETE USER WITH ID = " + id + "\n");
        userService.deleteById(id);
    }

}
