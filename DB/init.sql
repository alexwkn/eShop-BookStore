
SET client_encoding = 'UTF8';

DROP TABLE IF EXISTS order_line CASCADE;
DROP TABLE IF EXISTS app_order CASCADE;
DROP TABLE IF EXISTS translation CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS discount CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS language CASCADE;
DROP TABLE IF EXISTS product_translation CASCADE;


CREATE TABLE language (
                          language_id CHAR(5) PRIMARY KEY
);

CREATE TABLE category (
                          id SERIAL PRIMARY KEY
);

CREATE TABLE discount (
                          code VARCHAR(50) PRIMARY KEY,
                          discount NUMERIC(5,2) NOT NULL CHECK (discount BETWEEN 0 AND 100),
                          is_valid BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE app_user (
                          id SERIAL PRIMARY KEY,
                          username VARCHAR(100) NOT NULL,
                          first_name VARCHAR (100) NOT NULL,
                          last_name VARCHAR (100) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          address TEXT,
                          enabled BOOLEAN NOT NULL DEFAULT TRUE,
                          phone_number VARCHAR(20),
                          CHECK (char_length(username) >= 3)
    );

CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    authority VARCHAR(100) NOT NULL,

    CONSTRAINT authorities_fk FOREIGN KEY(user_id) REFERENCES app_user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT authorities_unique UNIQUE (user_id, authority)
);

CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price NUMERIC(10,2) NOT NULL CHECK (price > 0),
                         description TEXT,
                         author VARCHAR(255),
                         image VARCHAR(255),
                         category_id INT,
                         CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
                             ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE app_order (
                           order_id SERIAL PRIMARY KEY,
                           user_id INT,
                           is_paid BOOLEAN NOT NULL DEFAULT FALSE,
                           total_price NUMERIC(10,2) NOT NULL DEFAULT 0,
                           CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES app_user(id)
                               ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE order_line (
                            order_id INT NOT NULL,
                            product_id INT NOT NULL,
                            quantity INT NOT NULL CHECK (quantity > 0),
                            price NUMERIC(10,2) NOT NULL CHECK (price > 0),
                            PRIMARY KEY (order_id, product_id),
                            CONSTRAINT fk_orderline_order FOREIGN KEY (order_id) REFERENCES app_order(order_id)
                                ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT fk_orderline_product FOREIGN KEY (product_id) REFERENCES product(id)
                                ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Table de traduction unique pour les catégories ET les produits.
-- entity_type distingue à quoi se rapporte la ligne ('category' ou 'product').
-- Pas de FK sur entity_id : elle référence category.id OU product.id selon entity_type,
-- une seule FK n'est donc pas possible avec ce design "une seule table".
CREATE TABLE translation (
                             language_id CHAR(5) NOT NULL,
                             entity_type VARCHAR(20) NOT NULL,
                             entity_id INT NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             description TEXT,
                             PRIMARY KEY (language_id, entity_type, entity_id),
                             CONSTRAINT fk_translation_language FOREIGN KEY (language_id) REFERENCES language(language_id)
                                 ON UPDATE CASCADE ON DELETE CASCADE,
                             CONSTRAINT chk_translation_entity_type CHECK (entity_type IN ('category', 'product'))
);

INSERT INTO app_user (id, username, first_name, last_name, password, email, address, enabled, phone_number) VALUES
(200,'admin', 'admin', 'admin','$2a$10$iecux.ngSTDfrmjdB.XmleLNiz6ATIycdlDg4ddbaUk0KVpMNOjaC','admin@admin.admin',NULL,true,'00000000000');

INSERT INTO authorities(user_id, authority) VALUES('200', 'ROLE_ADMIN');

INSERT INTO category (id) VALUES (1),(2),(3);

INSERT INTO discount (code, discount, is_valid) VALUES
('FREE', 10.00, TRUE),
('PROMO10', 10.00, TRUE);

INSERT INTO language (language_id) VALUES ('en'), ('fr');

INSERT INTO product (id, name, price, description, category_id, author,image) VALUES
                                                                           (1, '1984', 12.50, 'Très bon livre.', 1,'Georges Orwell' ,'1984.jpg'),
                                                                           (2, 'Germinal', 4.90, 'Livre en bon état.', 2,'Emile Zola' ,'germinal.jpg'),
                                                                           (3, 'La peste', 12.40, 'Livre qui fait réfléchir.', 1,'Alber Camus' ,'lapeste.jpg'),
                                                                           (4, 'Lampe de lecture', 9.50, NULL, 3, NULL,'lampe.jpg'),
                                                                           (5, 'Le meilleur des mondes', 15.00, 'Livre neuf.', 1,'Aldous Huxley' ,'lemeilleurdesmondes.jpg'),
                                                                            (6, 'Les fourmis', 3.20, 'Livre en bon état.', 2,'Bernard Werber' ,'lesfourmis.jpg'),
                                                                            (7, 'Marque-page', 1.50, NULL, 3, NULL,'marquepage.jpg');

INSERT INTO translation (language_id, entity_type, entity_id, name, description) VALUES
                                                             ('en','category',1,'New books',NULL),
                                                             ('en','category',2,'Used books',NULL),
                                                             ('en','category',3,'Accessories',NULL),
                                                             ('fr','category',1,'Livres neufs',NULL),
                                                             ('fr','category',2,'Livres occasions',NULL),
                                                             ('fr','category',3,'Accessoires',NULL),
                                                             ('fr','product',1,'1984','Très bon livre.'),
                                                             ('en','product',1,'1984','A great book.'),
                                                             ('fr','product',2,'Germinal','Livre en bon état.'),
                                                             ('en','product',2,'Germinal','Book in good condition.'),
                                                             ('fr','product',3,'La peste','Livre qui fait réfléchir.'),
                                                             ('en','product',3,'The Plague','A thought-provoking book.'),
                                                             ('fr','product',4,'Lampe de lecture',NULL),
                                                             ('en','product',4,'Reading lamp',NULL),
                                                             ('fr','product',5,'Le meilleur des mondes','Livre neuf.'),
                                                             ('en','product',5,'Brave New World','Brand new book.'),
                                                             ('fr','product',6,'Les fourmis','Livre en bon état.'),
                                                             ('en','product',6,'The Ants','Book in good condition.'),
                                                             ('fr','product',7,'Marque-page',NULL),
                                                             ('en','product',7,'Bookmark',NULL);
