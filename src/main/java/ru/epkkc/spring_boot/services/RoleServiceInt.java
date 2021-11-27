package ru.epkkc.spring_boot.services;

import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;
import ru.epkkc.spring_boot.model.User;

import java.util.List;

public interface RoleServiceInt {

    List<Role> findAll();

    void save(Role role);

    Role findByRoleType(RolesEnum rolesEnum);

    public User findRolesInDB(User user);
}
