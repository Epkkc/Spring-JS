package ru.epkkc.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.epkkc.spring_boot.dao.RolesDao;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;

import java.util.List;

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
}
