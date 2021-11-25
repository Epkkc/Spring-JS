package ru.epkkc.spring_boot.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String s) {
//        System.out.println("\nCONVERTING string " + s + "\n");
        if (s.equals("0")) {
            return null;
        }
        RolesEnum rolesEnum = RolesEnum.valueOf(s);
        return new Role(rolesEnum);
    }
}
