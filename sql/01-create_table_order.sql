CREATE TABLE orders (
    id UUID,
    customer_name VARCHAR(255),
    amount DECIMAL(13,2),
    status VARCHAR(255),
    PRIMARY KEY (id)
);