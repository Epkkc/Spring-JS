package ru.epkkc.spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;

@Repository
public interface RolesDao extends JpaRepository<Role, Long> {

    Role findByRoleType(RolesEnum rolesEnum);
}
