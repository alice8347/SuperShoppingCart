CREATE TABLE products (
    id INTEGER PRIMARY KEY, 
    name VARCHAR(100), 
    description VARCHAR(500), 
    price NUMBER(38, 2)
);

INSERT INTO products VALUES (1, 'Stainless Steel Cookware Set', '12 Ply, T304 Stainless Steel', 122.95);
INSERT INTO products VALUES (2, 'Fry Vegetable Cutter', 'Hand crank operation. Skid-proof rubber feet for safe operation.', 22.85);
INSERT INTO products VALUES (3, 'Mold Cutter', 'Ideal for using with fondant cakes, sugar paste, petal paste, marzipan DIY cooking or craft clays', 0.99);
INSERT INTO products VALUES (4, 'Kitchen Cookware Set', 'New Paula Deen 16-Piece Cookware Set (Salmon Color)', 69.99);
INSERT INTO products VALUES (5, 'Prepology Set of 2 Utensils', 'Used. Excellent condition!', 7.00);
INSERT INTO products VALUES (6, 'Apple Slicer Cutter', 'Package Included: 1 x Stainless Steel Fruit Slicer', 2.43);
INSERT INTO products VALUES (7, 'Wilton Cookie Cutter Set', '101 Cookie Cutters.', 14.54);
INSERT INTO products VALUES (8, 'Cuisinart Juice Extractor', 'Model: CJE-500', 67.97);
INSERT INTO products VALUES (9, 'ODI Solid Copper Mugs', '1-16 oz and 6-2 oz', 15.00);
INSERT INTO products VALUES (10, 'French Press 3 Cups Coffee And Tea Maker', 'The press measures 8" high and has a 20 ounce capacity', 14.99);

CREATE SEQUENCE PRODUCT_ID_GENERATOR START WITH 11;

CREATE TABLE cart (
    id INTEGER PRIMARY KEY, 
    userId INTEGER, 
    pId INTEGER, 
    pName VARCHAR(100), 
    pPrice NUMBER(38, 2), 
    pQuantity INTEGER, 
    ordered NUMBER(1), 
    cardId INTEGER, 
    shipAddress VARCHAR(100)
);

CREATE SEQUENCE CART_ID_GENERATOR START WITH 1;

CREATE TABLE shoppingUsers (
    id INTEGER PRIMARY KEY, 
    email VARCHAR(50), 
    password VARCHAR(20), 
    credits NUMBER(38, 2)
);

INSERT INTO shoppingUsers VALUES (1, 'Admin', 'Admin', 0);

CREATE SEQUENCE SHOPPINGUSERS_ID_GENERATOR START WITH 2;

CREATE TABLE comments (
    id INTEGER PRIMARY KEY, 
    pId INTEGER, 
    userId INTEGER, 
    rating VARCHAR(5),
    cContent VARCHAR(500), 
    cDate DATE
);

CREATE SEQUENCE COMMENTS_ID_GENERATOR START WITH 1;

CREATE TABLE cards (
    id INTEGER PRIMARY KEY, 
    userId INTEGER, 
    holderName VARCHAR(50), 
    cardNum NUMBER(16), 
    securityCode NUMBER(3), 
    expDate DATE, 
    billAddress VARCHAR(100)
);

CREATE SEQUENCE CARDS_ID_GENERATOR START WITH 1;