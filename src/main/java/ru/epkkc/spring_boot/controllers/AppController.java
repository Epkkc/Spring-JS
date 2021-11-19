package ru.epkkc.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.epkkc.spring_boot.dao.RolesDao;
import ru.epkkc.spring_boot.dao.UsersDao;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private final UsersDao usersDao;
    private final RolesDao rolesDao;

    @Autowired
    public AppController(UsersDao usersDao, RolesDao rolesDao) {
        this.usersDao = usersDao;
        this.rolesDao = rolesDao;
    }

    @GetMapping("/admin")
    public String usersTable(ModelMap model) {
        List<User> allUsers = usersDao.findAll();
        for (User user : allUsers) {
            System.out.println(user);
        }
        List<Role> roles = rolesDao.findAll();

        User user = new User();
        model.addAttribute("users_list", allUsers);
        model.addAttribute("user_add", user);
        model.addAttribute("user_update", user);
        model.addAttribute("allRoles", roles);
        return "users_table";
    }

    @PostMapping("/admin")
    public RedirectView addUser(@ModelAttribute(name = "user_add") User user) {
        usersDao.save(findRolesInDB(user));
        return new RedirectView("http://localhost:8080/admin");
    }

    @PatchMapping("/admin")
    public RedirectView patchUser(@ModelAttribute(name = "user_update") User user) {
        User userU = usersDao.findById(user.getId()).get();
        userU.updateState(findRolesInDB(user));
        usersDao.flush();
        return new RedirectView("http://localhost:8080/admin");
    }

    @DeleteMapping("/admin")
    public RedirectView removeUser(@RequestParam(name = "user_id") Long id) {
        usersDao.deleteById(id);
        return new RedirectView("http://localhost:8080/admin");
    }

    @GetMapping("/user")
    public String userPage(ModelMap model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("lastname", currentUser.getLastname());
        model.addAttribute("year_of_birth", currentUser.getYearOfBirth());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("password", currentUser.getPassword());
        return "user_page";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login_page";
    }

    private User findRolesInDB(User user) {
        user.setRoles(user.getRoles()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        if (!user.getRoles().isEmpty()) {
            List<Role> dbRoles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                Role role1 = rolesDao.findByRoleType(role.getRoleType());
                dbRoles.add(role1);
            }
            user.setRoles(dbRoles);
        }
        return user;
    }
}
