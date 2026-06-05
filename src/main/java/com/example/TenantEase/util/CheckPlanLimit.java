package com.example.TenantEase.util;

import com.example.TenantEase.enums.ResourceType;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckPlanLimit {
    ResourceType resource();
}
