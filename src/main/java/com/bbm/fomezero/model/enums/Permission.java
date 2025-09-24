package com.bbm.fomezero.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),
    RESTAURANT_CREATE("restaurant:create"),
    RESTAURANT_READ("restaurant:read"),
    RESTAURANT_UPDATE("restaurant:update"),
    RESTAURANT_DELETE("restaurant:delete"),
    DRIVER_CREATE("driver:create"),
    DRIVER_READ("driver:read"),
    DRIVER_UPDATE("driver:update"),
    DRIVER_DELETE("driver:delete");

    private final String permission;
}
