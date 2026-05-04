package com.example.TenantEase.util;

public interface RoleApiConstant {
    public final static String[] ADMIN = {"/tenant/addTenant", "/tenant/getAllTenantAsPerOwner", "/tenant/getById",
            "/property/addProperty",
            "/property/getProperty", "/property/getAllProperty", "/property/deleteProperty",
            // RoomController endpoints
            "/rooms/addRoom",
            "/rooms/updateRoom",
            "/rooms/deleteRoom",
            "/rooms/getAllRoom",
            "/rooms/getById",
            "/rooms/getByProperty",
            //Rent Controller endpoints
            "/rent/paymentDone",
            "/rent/getRentDetails",
            //Ticket Controller endpoints
            "/ticket/create",
            "/ticket/image/*",
            "/ticket/getImageByTicketId"
    };
    public final static String[] USER = {"/tenant/getTenantById"};
    public final static String[] SUPER_ADMIN = {
            "/tenant/getAllTenant",
            "/user/getAllUser", "/role/createRole", "/role/getById", "/role/getAll",
            "/permission/createPermission", "/permission/getAll"
    };
}
