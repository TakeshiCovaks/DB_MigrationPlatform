-- Sushko Database Schema for Task#2 Test
-- use MySQL

-- 1.Create SCHEMA or DATABASE

DROP SCHEMA IF EXISTS sushko_proj_test;
CREATE SCHEMA sushko_proj_test;
USE sushko_proj_test;

-- 2. TABLE.

-- 2.1
CREATE TABLE products_category(
	prod_cat_id INT NOT NULL AUTO_INCREMENT,
	prod_cat_name VARCHAR(45) NOT NULL,
	prod_cat_stock_presence BOOLEAN NOT NULL,
	prod_cat_count DOUBLE NOT NULL,
	PRIMARY KEY (prod_cat_id));
    
  
  -- 2.2
  CREATE TABLE workers (
  passpord_id INT NOT NULL,
  worker_name VARCHAR(100) NOT NULL,
  profession VARCHAR(150) NOT NULL,
  PRIMARY KEY (passpord_id),
  UNIQUE INDEX passpord_id_UNIQUE (passpord_id ASC));
  
  -- 2.3
  CREATE TABLE animals (
  name_animal VARCHAR(80) NOT NULL,
  pupulation INT NOT NULL,
  habitat VARCHAR(200) NOT NULL DEFAULT 'All plannet',
  PRIMARY KEY (name_animal));
  
  -- 2.4 create table for work trigger
CREATE TABLE work_trigger (
  date_del DATETIME NOT NULL,
  id_category INT NOT NULL,
  type_triggger VARCHAR(45)) ;  
  
 
-- 3. TRIGGERS

-- AFTER DELETE
CREATE TRIGGER aft_del_products_category AFTER DELETE ON products_category FOR EACH ROW
INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.prod_cat_id, 'delete trigger');

-- BEFORE UPDATE
CREATE TRIGGER before_upd_products_category
BEFORE UPDATE
ON products_category FOR EACH ROW
INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), OLD.prod_cat_id, 'before update trigger');

-- AFTER INSERT
CREATE TRIGGER after_ins_products_category
AFTER INSERT
   ON products_category FOR EACH ROW
   INSERT work_trigger(date_del, id_category, type_triggger) VALUES (CURTIME(), NEW.prod_cat_id, 'after insert trigger');

-- 4. CREATE SORED PROCEDURE
CREATE PROCEDURE insert_procedire (IN category VARCHAR(45))
	INSERT products_category(prod_cat_id, prod_cat_name, prod_cat_stock_presence, prod_cat_count) VALUES(DEFAULT, (category), TRUE, 0);

CREATE PROCEDURE insert_animals ()
	INSERT animals(name_animal, pupulation, habitat) VALUES('sparrow', 100000, DEFAULT);
--	INSERT animals(name_animal, pupulation, habitat) VALUES('dog', 500000, DEFAULT);
--	INSERT animals(name_animal, pupulation, habitat) VALUES('lion', 500000, 'Africa');


-- 5. CREATE FUNCTION
-- 5.1 PLUS
CREATE FUNCTION plus (x INTEGER, y INTEGER)
RETURNS INTEGER
	RETURN x + y;

-- 5.2 MINUS
CREATE FUNCTION minus (x INTEGER, y INTEGER)
RETURNS INTEGER
	RETURN x - y;

-- 6. CREATE VIEWS
CREATE VIEW view_product_category AS SELECT * FROM products_category WHERE prod_cat_count > 0;
CREATE VIEW get_all_animals AS SELECT name_animal FROM animals;

