package ru.epkkc.spring_boot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Enumerated(value = EnumType.STRING)
    @Column
    private RolesEnum roleType = RolesEnum.USER;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users;

    public Role(RolesEnum roleType) {
        this.roleType = roleType;
    }

    public Role() {
    }

    public Role(Long role_id, RolesEnum roleType) {
        this.role_id = role_id;
        this.roleType = roleType;
    }

    public RolesEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RolesEnum roleType) {
        this.roleType = roleType;
    }

    public Long getId() {
        return role_id;
    }

    @Override
    public String toString() {
        return roleType.toString();
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleType == role.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType);
    }
}
