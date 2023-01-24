package controller.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static controller.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(USER_READ,
                        USER_WRITE,
                        EQUIPMENT_ITEM_READ,
                        EQUIPMENT_ITEM_WRITE,
                        TOKEN_READ,
                        TOKEN_WRITE)),
    ENGINEER(Sets.newHashSet(EQUIPMENT_ITEM_READ)),
    DEPARTMENT_ADMIN(Sets.newHashSet(USER_READ,
                                    EQUIPMENT_ITEM_READ,
                                    EQUIPMENT_ITEM_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
