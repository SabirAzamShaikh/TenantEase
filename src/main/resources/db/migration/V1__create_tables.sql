-- ============================================
-- V1__create_tables.sql
-- ============================================

CREATE TABLE image_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    image_data LONGBLOB
);

CREATE TABLE permission (
    permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by VARCHAR(36) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_by VARCHAR(36),
    updated_at DATETIME(6)
);

CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    created_by VARCHAR(36) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_by VARCHAR(36),
    updated_at DATETIME(6)
);

CREATE TABLE role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES role (role_id),
    CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES permission (permission_id)
);

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    password VARCHAR(255),
    user_type VARCHAR(255),
    email VARCHAR(255),
    contact_number VARCHAR(255),
    created_by VARCHAR(36) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_by VARCHAR(36),
    updated_at DATETIME(6),
    status VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES role (role_id)
);

CREATE TABLE subscription_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    price BIGINT NOT NULL,
    billing_cycle VARCHAR(255) NOT NULL,
    max_properties BIGINT NOT NULL,
    max_rooms BIGINT NOT NULL,
    max_tenants BIGINT NOT NULL,
    active BIT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6)
);

CREATE TABLE user_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    plan_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    razorpay_subscription_id VARCHAR(255),
    start_date DATETIME(6),
    end_date DATETIME(6),
    auto_renew BIT NOT NULL,
    activated_at DATETIME(6),
    cancelled_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6),
    version BIGINT,
    CONSTRAINT fk_user_subscription_user FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT fk_user_subscription_plan FOREIGN KEY (plan_id) REFERENCES subscription_plan (id)
);

CREATE TABLE subscription_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    old_plan_id BIGINT,
    new_plan_id BIGINT,
    action_type VARCHAR(255) NOT NULL,
    amount_paid BIGINT,
    action_date DATETIME(6) NOT NULL,
    remarks VARCHAR(255),
    CONSTRAINT fk_subscription_history_user FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT fk_subscription_history_old_plan FOREIGN KEY (old_plan_id) REFERENCES subscription_plan (id),
    CONSTRAINT fk_subscription_history_new_plan FOREIGN KEY (new_plan_id) REFERENCES subscription_plan (id)
);

CREATE TABLE plan_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    property_count INT NOT NULL,
    room_count INT NOT NULL,
    tenant_count INT NOT NULL,
    updated_at DATETIME(6),
    CONSTRAINT fk_plan_usage_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE payment_transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    user_subscription_id BIGINT,
    amount BIGINT NOT NULL,
    currency VARCHAR(255) NOT NULL,
    razorpay_order_id VARCHAR(255),
    razorpay_payment_id VARCHAR(255),
    razorpay_subscription_id VARCHAR(255),
    payment_status VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255),
    transaction_date DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    CONSTRAINT fk_payment_transaction_user FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT fk_payment_transaction_subscription FOREIGN KEY (user_subscription_id) REFERENCES user_subscription (id)
);

CREATE TABLE property (
    property_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    address VARCHAR(255),
    total_rooms INT NOT NULL,
    owner_name VARCHAR(255),
    total_floors INT NOT NULL
);

CREATE TABLE property_property_image_path (
    property_property_id BIGINT NOT NULL,
    property_image_path VARCHAR(255),
    CONSTRAINT fk_property_image_property FOREIGN KEY (property_property_id) REFERENCES property (property_id)
);

CREATE TABLE room (
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(255),
    description VARCHAR(255),
    room_type VARCHAR(255),
    rent_amount DOUBLE NOT NULL,
    deposit_amount DOUBLE NOT NULL,
    available BIT NOT NULL,
    furnishing_type VARCHAR(255),
    occupancy_limit INT NOT NULL,
    floor INT NOT NULL,
    attached_bath BIT NOT NULL,
    amenities VARCHAR(255),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    created_by VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    property_id BIGINT NOT NULL,
    CONSTRAINT fk_room_property FOREIGN KEY (property_id) REFERENCES property (property_id)
);

CREATE TABLE room_room_image_path (
    room_room_id BIGINT NOT NULL,
    room_image_path VARCHAR(255),
    CONSTRAINT fk_room_image_room FOREIGN KEY (room_room_id) REFERENCES room (room_id)
);

CREATE TABLE tenant (
    tenant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    adhar_number VARCHAR(255) NOT NULL,
    room_number VARCHAR(255),
    deposite_amount BIGINT,
    is_tenant BIT NOT NULL,
    total_stay_month INT NOT NULL,
    room_id BIGINT NOT NULL,
    create_time DATE NOT NULL,
    created_by VARCHAR(255),
    rent_amount BIGINT NOT NULL,
    rent_payment_day INT NOT NULL,
    CONSTRAINT fk_tenant_room FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE tenant_rent (
    rent_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    month_number INT NOT NULL,
    year INT NOT NULL,
    due_of_this_month BIGINT,
    is_paid BIT NOT NULL,
    due_date DATE,
    paid_date DATE,
    payment_mode VARCHAR(255),
    remarks VARCHAR(255),
    tenant_id BIGINT,
    CONSTRAINT fk_tenant_rent_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (tenant_id)
);

CREATE TABLE ticket (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_topic VARCHAR(255),
    message VARCHAR(255),
    crated_at DATE,
    status VARCHAR(255),
    resolved_date DATE
);

CREATE TABLE ticket_image_path (
    ticket_ticket_id INT NOT NULL,
    image_path VARCHAR(255),
    CONSTRAINT fk_ticket_image_ticket FOREIGN KEY (ticket_ticket_id) REFERENCES ticket (ticket_id)
);
