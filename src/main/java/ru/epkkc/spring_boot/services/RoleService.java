package ru.epkkc.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.epkkc.spring_boot.dao.RolesDao;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;
import ru.epkkc.spring_boot.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
public class RoleService implements RoleServiceInt{

    private RolesDao roleDao;

    @Autowired
    public RoleService(RolesDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findByRoleType(RolesEnum rolesEnum) {
        return roleDao.findByRoleType(rolesEnum);
    }

    @Override
    public User findRolesInDB(User user) {
        user.setRoles(user.getRoles()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        if (!user.getRoles().isEmpty()) {
            List<Role> dbRoles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                Role role1 = findByRoleType(role.getRoleType());
                dbRoles.add(role1);
            }
            user.setRoles(dbRoles);
        }
        return user;
    }

}
