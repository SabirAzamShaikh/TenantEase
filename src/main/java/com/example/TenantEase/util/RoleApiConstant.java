package com.example.TenantEase.util;

public interface RoleApiConstant {
    public final static String[] ADMIN = {"/tenant/addTenant", "/tenant/getAllTenant", "/property/addProperty",
            "/property/getProperty", "/property/getAllProperty", "/property/deleteProperty",
            // RoomController endpoints
            "/rooms/addRoom",
            "/rooms/updateRoom",
            "/rooms/deleteRoom",
            "/rooms/getAllRoom",
            "/rooms/getById",
            "/rooms/getByProperty"
    };
    public final static String[] USER = {"/tenant/getTenantById"};
    public final static String[] SUPER_ADMIN = {
            "/user/getAllUser", "/role/createRole", "/role/getById", "/role/getAll",
            "/permission/createPermission", "/permission/getAll"
    };
}
