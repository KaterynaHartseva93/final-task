-- Dumping database structure for market
DROP DATABASE IF EXISTS `market`;
CREATE DATABASE IF NOT EXISTS `market` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `market`;

-- All definitions about products related tables
DROP TABLE IF EXISTS `brands`;
CREATE TABLE IF NOT EXISTS `brands` (
  id int(11) NOT NULL AUTO_INCREMENT UNIQUE,
  name varchar(150) NOT NULL UNIQUE,
  PRIMARY KEY (id));	
	
INSERT INTO brands (id, name) VALUES
	(1, 'Mashalando'),
	(2, 'Hlbandage'),
	(3, 'Vestido');
	
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  id int(11) NOT NULL AUTO_INCREMENT UNIQUE,
  name varchar(150) NOT NULL UNIQUE,
  PRIMARY KEY (id));	
	
INSERT INTO categories (name) VALUES
	('Dresses'),
	('Trousers'),
	('Skirts'),
	('Blouses'),
	('Tops'),
	('Jeans'),
	('Jackets'),
	('Shorts'),
	('Sweaters'),
	('Pajamas'),
	('Coats'),
	('Capris'),
	('Suits'),
	('Activewear');

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  id int(11) NOT NULL AUTO_INCREMENT UNIQUE,
  name varchar(150) NOT NULL,
  price double NOT NULL,
  id_brand int(11) NOT NULL,
  id_category int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_brand) REFERENCES brands(id) ON DELETE CASCADE,
  FOREIGN KEY (id_category) REFERENCES categories(id) ON DELETE CASCADE
);

DELETE FROM `products`;
INSERT INTO products (id, name, price, id_brand, id_category) VALUES
	(1, 'Mashalando Dress', 3888.58 , 1, 1),
	(2, 'Hlbandage imitation leather Dress', 5072.06 , 2, 1),
	(3, 'Hlbandage Magic 2016 Dress', 1958.38 ,2, 1),
	(4, 'Vestido Dress', 1035.81, 3, 1);

	
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE IF NOT EXISTS `product_info` (
  id_product int(11) NOT NULL,
  sell_count int(11) NOT NULL,
  image_identifier varchar(150),
  creation_timestamp BIGINT NOT NULL,	
  FOREIGN KEY (id_product) REFERENCES products(id) ON DELETE CASCADE);
	
INSERT INTO product_info (id_product, sell_count, creation_timestamp, image_identifier) VALUES
	(1, 3, 1455801552725, "1"),
	(2, 34, 1455801552735, "2"),
	(3, 21, 1455801552755, "3"),
	(4, 12, 1455801552715, "4");	
	
DROP TABLE IF EXISTS `product_sizes`;
CREATE TABLE IF NOT EXISTS `product_sizes` (
  id_product int(11) NOT NULL,
  size enum('XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL') NOT NULL,	
  FOREIGN KEY (id_product) REFERENCES products(id) ON DELETE CASCADE);

INSERT INTO product_sizes (id_product, size) VALUES
	(1, 'L'),
	(1, 'S'),
	(1, 'M'),
	(2, 'L'),
	(2, 'M'),
	(3, 'XXL'),
	(3, 'XXXL'),
	(4, 'S'),
	(4, 'L');
	
-- All definitions about users related tables	
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(35) NOT NULL UNIQUE,
  password varchar(34) NOT NULL,
  role enum('ADMIN', 'CLIENT') NOT NULL,
  first_name varchar(20) NOT NULL,
  last_name varchar(20) NOT NULL,
  gender enum('MALE', 'FEMALE') NOT NULL,
  status enum('ACTIVE', 'BLOCKED') DEFAULT 'ACTIVE',
  PRIMARY KEY (id));
  
  DELETE FROM `users`;
  INSERT INTO users (id, email, password, role, first_name, last_name, gender) VALUES
  (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN', 'Ivan', 'Ivanov', 'MALE'),
  (2, 'kateryna_hartseva@gmail.com', '6f28f0afcf7f54b0c2a21954bf314677', 'CLIENT', 'Kateryna', 'Hartseva', 'FEMALE'),
  (3, 'snejana.denisovna@gmail.com', '6f28f0afcf7f54b0c2a21954bf314677', 'CLIENT', 'Snejana', 'Denysovna', 'MALE');
  
  -- All definitions about orders related tables
  
  CREATE TABLE IF NOT EXISTS `orders` (
	id int(11) NOT NULL AUTO_INCREMENT,
	owner_email varchar(35) NOT NULL,
	status enum('REGISTERED', 'PAID', 'CANCELLED') NOT NULL DEFAULT 'REGISTERED',
	creation_timestamp BIGINT NOT NULL,
	PRIMARY KEY (id));
	  
  DELETE FROM `orders`;
  INSERT INTO orders (id, owner_email, status, creation_timestamp) VALUES
  (1, 'kateryna_hartseva@gmail.com', 'REGISTERED', 1455801552725);

  CREATE TABLE IF NOT EXISTS `order_units` (
  	id int(11) NOT NULL AUTO_INCREMENT,
  	id_order int(11) NOT NULL,
  	id_product int(11) NOT NULL,
  	product_name varchar(150) NOT NULL,
  	product_brand varchar(150) NOT NULL,
  	product_category varchar(150) NOT NULL,
  	product_price double NOT NULL,
  	product_count int(11) NOT NULL,
  	product_size enum('XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL') NOT NULL,
  	FOREIGN KEY (id_order) REFERENCES orders(id) ON DELETE CASCADE,
  	PRIMARY KEY (id));

  	DELETE FROM `order_units`;
  INSERT INTO order_units (id, id_order, id_product, product_name, product_brand, product_category, product_price, product_count, product_size) VALUES
  (1, 1, 1, 'Mashalando Dress', 'Brand #1', 'Category #2', 3888.58, 3, 'XXL'),
  (2, 1, 1, 'Mashalando Dress', 'Brand #1', 'Category #2', 3888.58, 3, 'XL');