--liquibase formatted sql

--changeset gunjan:001-create-orders
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    amount DOUBLE NOT NULL,

    customer_id BIGINT NOT NULL,

    version BIGINT DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    created_by VARCHAR(100) DEFAULT 'system',
    updated_by VARCHAR(100)
);