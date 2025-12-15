CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    title VARCHAR(400) NOT NULL,
    description VARCHAR(400),
    img_path VARCHAR(255),
    price BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS order_products (
    order_id INTEGER REFERENCES orders ON DELETE CASCADE,
    product_id INTEGER REFERENCES products,
    quantity INTEGER NOT NULL,
    CONSTRAINT order_products_unique_id UNIQUE(order_id, product_id),
    CONSTRAINT order_products_count_positive CHECK (quantity > 0)
);

CREATE TABLE IF NOT EXISTS carts (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cart_products (
    cart_id INTEGER REFERENCES carts ON DELETE CASCADE,
    product_id INTEGER REFERENCES products,
    quantity INTEGER NOT NULL,
    CONSTRAINT cart_products_unique_id UNIQUE(cart_id, product_id),
    CONSTRAINT cart_products_count_positive CHECK (quantity > 0)
);