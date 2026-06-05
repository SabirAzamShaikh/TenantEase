-- ============================================
-- Section 1: Roles
-- ============================================

INSERT IGNORE INTO role (role_id, description, created_by, created_at, updated_by, updated_at) 
VALUES (1, 'SUPER_ADMIN', 'SYSTEM', NOW(), NULL, NULL);

INSERT IGNORE INTO role (role_id, description, created_by, created_at, updated_by, updated_at) 
VALUES (2, 'ADMIN', 'SYSTEM', NOW(), NULL, NULL);

INSERT IGNORE INTO role (role_id, description, created_by, created_at, updated_by, updated_at) 
VALUES (3, 'USER', 'SYSTEM', NOW(), NULL, NULL);

-- ============================================
-- Section 2: Super Admin User
-- ============================================

INSERT IGNORE INTO user (
    full_name, email, password, user_type, contact_number, status, created_by, created_at, updated_by, updated_at
) VALUES (
    'Sabir Azam Shaikh',
    'www.sabirazamshaikh313@gmail.com',
    '$2a$10$a81Eyi3MOLQdvPAosCO9Eu08yYg4Tpz1pTjLQpi./YWqINiyV3i2S',
    'SUPER_ADMIN', 
    '8421682861',
    'Active', 
    'SYSTEM', 
    NOW(), 
    NULL, 
    NULL
);

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT user_id, 1 FROM user WHERE email = 'www.sabirazamshaikh313@gmail.com';

-- ============================================
-- Section 3: Subscription Plans
-- ============================================

INSERT IGNORE INTO subscription_plan (
    plan_name, description, price, billing_cycle, max_properties, max_rooms, max_tenants, active, created_at, updated_at
) VALUES (
    'FREE', 
    'Free plan for getting started', 
    0, 
    'MONTHLY', 
    1, 
    5, 
    5, 
    true, 
    NOW(), 
    NOW()
);

INSERT IGNORE INTO subscription_plan (
    plan_name, description, price, billing_cycle, max_properties, max_rooms, max_tenants, active, created_at, updated_at
) VALUES (
    'PRO', 
    'Pro plan for growing landlords', 
    999, 
    'MONTHLY', 
    10, 
    500, 
    1000, 
    true, 
    NOW(), 
    NOW()
);

INSERT IGNORE INTO subscription_plan (
    plan_name, description, price, billing_cycle, max_properties, max_rooms, max_tenants, active, created_at, updated_at
) VALUES (
    'PREMIUM', 
    'Premium plan with unlimited access', 
    2999, 
    'MONTHLY', 
    -1, 
    -1, 
    -1, 
    true, 
    NOW(), 
    NOW()
);
