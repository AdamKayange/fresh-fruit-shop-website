CREATE DATABASE IF NOT EXISTS fruitshop;
USE fruitshop;

-- Users Table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Fruits Table
CREATE TABLE fruits (
    fruit_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    description TEXT,
    category VARCHAR(50),
    in_stock BOOLEAN DEFAULT TRUE,
    quantity INT DEFAULT 0,
    origin VARCHAR(100),
    nutrition_info TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders Table (must come before order_items)
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    total_amount DECIMAL(10,2),
    status ENUM('pending', 'processing', 'shipped', 'delivered') DEFAULT 'pending',
    shipping_name VARCHAR(100),
    shipping_address TEXT,
    shipping_city VARCHAR(50),
    shipping_zipcode VARCHAR(20),
    shipping_phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Cart Table
CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    fruit_id INT,
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (fruit_id) REFERENCES fruits(fruit_id) ON DELETE CASCADE
);

-- Order Items Table (name fixed and FK columns types fixed)
CREATE TABLE order_items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    fruit_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (fruit_id) REFERENCES fruits(fruit_id) ON DELETE CASCADE
);

-- Favorites Table
CREATE TABLE favorites (
    favorite_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    fruit_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (fruit_id) REFERENCES fruits(fruit_id) ON DELETE CASCADE,
    UNIQUE KEY unique_favorite (user_id, fruit_id)
);

-- Sample Data: Users
INSERT INTO users (name, email, password, is_admin) VALUES
('Admin User', 'admin@fruitshop.com', 'admin123', TRUE),
('John Doe', 'john@example.com', 'password123', FALSE),
('Jane Smith', 'jane@example.com', 'password123', FALSE);

-- Sample Data: Fruits
INSERT INTO fruits (name, price, image_url, description, category, in_stock, quantity, origin, nutrition_info) VALUES
('Fresh Strawberries', 4.99, 'strawberries.jpg', 'Sweet, juicy strawberries perfect for snacking', 'Berries', TRUE, 50, 'California', 'Rich in Vitamin C and antioxidants'),
('Organic Bananas', 2.49, 'bananas.jpg', 'Naturally ripened organic bananas', 'Tropical', TRUE, 75, 'Ecuador', 'High in potassium and vitamin B6'),
('Crisp Red Apples', 3.99, 'apples.jpg', 'Crunchy red apples with sweet-tart flavor', 'Tree Fruits', TRUE, 60, 'Washington', 'Good source of fiber and vitamin C'),
('Fresh Oranges', 5.99, 'oranges.jpg', 'Juicy valencia oranges', 'Citrus', TRUE, 40, 'Florida', 'Excellent source of vitamin C'),
('Ripe Avocados', 7.99, 'avocados.jpg', 'Creamy Hass avocados', 'Exotic', TRUE, 30, 'Mexico', 'Rich in healthy fats and fiber'),
('Fresh Blueberries', 6.99, 'blueberries.jpg', 'Antioxidant-rich blueberries', 'Berries', TRUE, 25, 'Maine', 'Packed with antioxidants'),
('Golden Pineapple', 8.99, 'pineapple.jpg', 'Sweet tropical pineapple', 'Tropical', TRUE, 20, 'Costa Rica', 'High in vitamin C and bromelain'),
('Red Grapes', 4.49, 'grapes.jpg', 'Sweet red grapes', 'Grapes', TRUE, 45, 'Chile', 'Contains antioxidants and vitamin C');
