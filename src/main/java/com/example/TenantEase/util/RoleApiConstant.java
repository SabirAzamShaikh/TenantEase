package com.example.TenantEase.util;

public interface RoleApiConstant {

    public final static String[] ADMIN = {"tenant/addTenant", "tenant/getAllTenant"};
    public final static String[] USER = {"tenant/getTenantById"};
    public final static String[] SUPER_ADMIN = {"user/getAllUser", "role/**", "permission/**"};
}
