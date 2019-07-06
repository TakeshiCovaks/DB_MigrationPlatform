

DROP SCHEMA IF EXISTS sushko_proj_for_test;
CREATE SCHEMA sushko_proj_for_test;
USE sushko_proj_for_test;




CREATE TABLE products_category(
	prod_cat_id INT NOT NULL AUTO_INCREMENT,
	prod_cat_name VARCHAR(45) NOT NULL,
	prod_cat_stock_presence BOOLEAN NOT NULL,
	prod_cat_count DOUBLE NOT NULL,
	PRIMARY KEY (prod_cat_id));


CREATE TABLE products(
	products_id INT NOT NULL AUTO_INCREMENT,
    products_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY(products_id),
    INDEX category_id (category_id ASC),
    CONSTRAINT category
		FOREIGN KEY (category_id)
        REFERENCES products_category (prod_cat_id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION);        


 CREATE TABLE sales (
  id_sales INT NOT NULL AUTO_INCREMENT,
  sales_name VARCHAR(45) NULL DEFAULT 'without_name',
  department VARCHAR(100) NOT NULL,
  salescol VARCHAR(45) NULL,
  PRIMARY KEY (id_sales));
  

  CREATE TABLE workers (
  passpord_id INT NOT NULL,
  worker_name VARCHAR(100) NOT NULL,
  profession VARCHAR(150) NOT NULL,
  PRIMARY KEY (passpord_id),
  UNIQUE INDEX passpord_id_UNIQUE (passpord_id ASC));
  

  CREATE TABLE animals (
  name_animal VARCHAR(80) NOT NULL,
  pupulation INT NOT NULL,
  habitat VARCHAR(200) NOT NULL DEFAULT 'All plannet',
  PRIMARY KEY (name_animal));
  

CREATE TABLE work_trigger (
  date_del DATETIME NOT NULL,
  id_category INT NOT NULL,
  type_triggger VARCHAR(45)) ;  
  

  CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  author VARCHAR(45) NOT NULL DEFAULT 'Unknown',
  PRIMARY KEY (`id`));
  

CREATE TABLE car_category(
	car_cat_id INT NOT NULL AUTO_INCREMENT,
	car_cat_name VARCHAR(45) NOT NULL,
	car_cat_stock_presence BOOLEAN NOT NULL,
	car_cat_count DOUBLE NOT NULL,
	PRIMARY KEY (car_cat_id));


CREATE TABLE car(
	car_id INT NOT NULL AUTO_INCREMENT,
    car_name VARCHAR(100) NOT NULL,
    car_type VARCHAR(100) NOT NULL DEFAULT 'Other',
    PRIMARY KEY(car_id),
    INDEX car_id (car_id ASC),
    CONSTRAINT car
		FOREIGN KEY (car_id)
        REFERENCES car_category (car_cat_id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION); 

  
-- DELIMITER $$
--
-- CREATE TRIGGER aft_del_products_category
-- AFTER DELETE
--    ON products_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.prod_cat_id, 'delete trigger');
-- END;
--
--
-- CREATE TRIGGER before_upd_products_category
-- BEFORE UPDATE
--    ON products_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.prod_cat_id, 'before update trigger');
-- END;
--
--
-- CREATE TRIGGER after_ins_products_category
-- AFTER INSERT
--    ON products_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), NEW.prod_cat_id, 'after insert trigger');
-- END;$$
--
-- DELIMITER ;
--
-- DELIMITER $$
--
-- CREATE TRIGGER aft_del_car_category
-- AFTER DELETE
--    ON car_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.car_cat_id, 'delete trigger');
-- END;
--
-- CREATE TRIGGER before_upd_car_category
-- BEFORE UPDATE
--    ON car_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.car_cat_id, 'before update trigger');
-- END;
--
-- CREATE TRIGGER after_ins_car_category
-- AFTER INSERT
--    ON car_category FOR EACH ROW
--
-- BEGIN
-- 	INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), NEW.car_cat_id, 'after insert trigger');
-- END;$$
--
-- DELIMITER ;
--
--
-- DELIMITER $$
-- CREATE PROCEDURE insert_procedire (IN category VARCHAR(45))
-- BEGIN
-- 	INSERT products_category(prod_cat_id, prod_cat_name, prod_cat_stock_presence, prod_cat_count) VALUES(DEFAULT, (category), TRUE, 0);
-- END;$$
-- DELIMITER ;
--
-- DELIMITER $$
-- CREATE PROCEDURE insert_animals ()
-- BEGIN
-- 	INSERT animals(name_animal, pupulation, habitat) VALUES('sparrow', 100000, DEFAULT);
-- 	INSERT animals(name_animal, pupulation, habitat) VALUES('dog', 500000, DEFAULT);
-- 	INSERT animals(name_animal, pupulation, habitat) VALUES('lion', 500000, 'Africa');
-- END;$$
-- DELIMITER ;
--
-- DELIMITER $$
-- CREATE PROCEDURE insert_workers ()
-- BEGIN
-- 	INSERT workers(passpord_id, worker_name, profession) VALUES(123456, 'Ivanov Ivan', 'layer');
-- 	INSERT workers(passpord_id, worker_name, profession) VALUES(262615, 'Petrov Petr', 'developer');
-- 	INSERT workers(passpord_id, worker_name, profession) VALUES(987456, 'Alex Shtane', 'HR');
--
-- END;$$
-- DELIMITER ;
--
--
-- DELIMITER $$
-- CREATE FUNCTION plus (x INTEGER, y INTEGER)
-- RETURNS INTEGER
-- BEGIN
-- 	RETURN x + y;
-- END$$
-- DELIMITER ;
--
--
-- DELIMITER $$
-- CREATE FUNCTION minus (x INTEGER, y INTEGER)
-- RETURNS INTEGER
-- BEGIN
-- 	RETURN x - y;
-- END$$
-- DELIMITER ;
--
--
-- DELIMITER $$
-- CREATE FUNCTION welcome (x VARCHAR(45))
-- RETURNS VARCHAR(45)
-- BEGIN
-- 	RETURN concat('Hello, ', x, '!!!');
-- END$$
-- DELIMITER ;


CREATE VIEW view_product_category AS SELECT * FROM products_category WHERE prod_cat_count > 0;
CREATE VIEW get_all_animals AS SELECT name_animal FROM animals;
CREATE VIEW get_workers AS SELECT worker_name, profession FROM workers;
 
    