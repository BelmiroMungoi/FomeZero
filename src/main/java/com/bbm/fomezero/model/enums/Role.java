package com.bbm.fomezero.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bbm.fomezero.model.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    CUSTOMER_CREATE,
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    RESTAURANT_CREATE,
                    RESTAURANT_READ,
                    RESTAURANT_UPDATE,
                    RESTAURANT_DELETE,
                    DRIVER_CREATE,
                    DRIVER_READ,
                    DRIVER_UPDATE,
                    DRIVER_DELETE
            )
    ),
    CUSTOMER(
            Set.of(
                    CUSTOMER_CREATE,
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE
            )
    ),
    RESTAURANT_OWNER(
            Set.of(
                    RESTAURANT_CREATE,
                    RESTAURANT_READ,
                    RESTAURANT_UPDATE,
                    RESTAURANT_DELETE
            )
    ),
    DRIVER(
            Set.of(
                    DRIVER_CREATE,
                    DRIVER_READ,
                    DRIVER_UPDATE,
                    DRIVER_DELETE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return authorities;
    }
}