drop table CART_ITEMS;
drop table CLOTHING_SIZE_STOCKS;
drop table METAL.ITEMS;
drop table METAL.BANDS;
drop table METAL.LABELS;
drop table METAL.CATEGORIES;
drop table METAL.COUNTRIES;
drop table METAL.GENRES;
drop table METAL.IMAGES;
drop table METAL.USERS;
drop table METAL.ROLES;
drop table METAL.SIZES;

create table METAL.BANDS (ID integer primary key, NAME varchar(100), GENRE_ID integer, WEBSITE varchar(10000), LABEL_ID integer, COUNTRY_ID integer, IMAGE_ID integer);
create table METAL.CATEGORIES(ID integer primary key, NAME varchar(100));
create table METAL.SIZES(ID integer primary key, SIZE varchar(10));
create table METAL.COUNTRIES(ID integer primary key, NAME varchar(100));
create table METAL.GENRES(ID integer primary key, NAME varchar(100));
create table METAL.IMAGES(ID integer primary key, ADDRESS varchar(10000));
create table METAL.ITEMS(ID integer primary key, NAME varchar(100), PRICE double, STOCK integer, CATEGORY_ID integer, IMAGE_ID integer, BAND_ID integer, GENRE_ID integer, LABEL_ID integer);
create table METAL.CLOTHING_SIZE_STOCKS(ID integer primary key, ITEM_ID integer, SIZE_ID integer, STOCK integer);
create table METAL.LABELS (ID integer primary key, NAME varchar(100), WEBSITE varchar(10000), COUNTRY_ID integer, IMAGE_ID integer);
create table METAL.ROLES(ID  integer primary key, NAME varchar(100));
create table METAL.USERS(ID  integer primary key, USERNAME varchar(100), PASSWORD varchar(25), ROLE_ID integer);
create table METAL.CART_ITEMS(ID integer primary key, ITEM_ID integer, QUANTITY integer, PRICE double, SIZE_ID integer, USER_ID integer);

ALTER TABLE METAL.USERS ADD FOREIGN KEY (ROLE_ID) REFERENCES METAL.ROLES (ID);
ALTER TABLE METAL.LABELS ADD FOREIGN KEY (COUNTRY_ID) REFERENCES METAL.COUNTRIES (ID);
ALTER TABLE METAL.LABELS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (LABEL_ID) REFERENCES METAL.LABELS (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (GENRE_ID) REFERENCES METAL.GENRES (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (COUNTRY_ID) REFERENCES METAL.COUNTRIES (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (CATEGORY_ID) REFERENCES METAL.CATEGORIES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (BAND_ID) REFERENCES METAL.BANDS (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (GENRE_ID) REFERENCES METAL.GENRES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (LABEL_ID) REFERENCES METAL.LABELS (ID);
ALTER TABLE METAL.CLOTHING_SIZE_STOCKS ADD FOREIGN KEY (ITEM_ID) REFERENCES METAL.ITEMS (ID);
ALTER TABLE METAL.CLOTHING_SIZE_STOCKS ADD FOREIGN KEY (SIZE_ID) REFERENCES METAL.SIZES (ID);
ALTER TABLE METAL.CART_ITEMS ADD FOREIGN KEY (ITEM_ID) REFERENCES METAL.ITEMS (ID);
ALTER TABLE METAL.CART_ITEMS ADD FOREIGN KEY (SIZE_ID) REFERENCES METAL.SIZES (ID);
ALTER TABLE METAL.CART_ITEMS ADD FOREIGN KEY (USER_ID) REFERENCES METAL.USERS (ID);

INSERT INTO METAL.ROLES (ID, "NAME") 
	VALUES (1, 'user');
INSERT INTO METAL.ROLES (ID, "NAME") 
	VALUES (2, 'admin');

INSERT INTO METAL.USERS (ID, USERNAME, PASSWORD, ROLE_ID) 
	VALUES (1, 'user', 'user', 1);
INSERT INTO METAL.USERS (ID, USERNAME, PASSWORD, ROLE_ID) 
	VALUES (2, 'admin', 'admin', 2);

INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (1, 'Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (2, 'Black Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (3, 'Doom Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (4, 'Melodic Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (5, 'Technical Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (6, 'Blackened Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (7, 'Progressive Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (8, 'Progressive Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (9, 'Symphonic Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (10, 'Symphonic Death Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (11, 'Symphonic Black Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (12, 'Folk Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (13, 'Viking/Pagan Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (14, 'Metalcore');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (15, 'Deathcore');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (16, 'Hardcore');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (17, 'Djent');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (18, 'Experimental/Avant-Garde Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (19, 'Atmospheric Black Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (20, 'Stoner Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (21, 'Power Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (22, 'Thrash Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (23, 'Heavy Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (24, 'Rock''n''Roll');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (25, 'Sludge');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (26, 'Industrial/Electronic');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (27, 'Gothic');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (28, 'Groove Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (29, 'Speed Metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (30, 'Neo-classical/Shred');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (31, 'NU metal');
INSERT INTO METAL.GENRES (ID, "NAME") 
	VALUES (32, 'Oriental Metal');

INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (1, 'Sweden');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (2, 'Finland');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (3, 'Netherlands');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (4, 'USA');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (5, 'Germany');


INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (1, 'https://www.metal-archives.com/images/8/7/6/8/87685_logo.jpg?4307');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (2, 'https://www.metal-archives.com/images/1/5/1/7/15175_label.jpg?4829');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (3, 'https://mir-s3-cdn-cf.behance.net/project_modules/disp/8480b510017273.5627a4c489f2f.jpg');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (4, 'https://img.cdandlp.com/2017/09/imgL/118954397.jpg');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (5, 'https://i.pinimg.com/originals/7e/55/1a/7e551ad35ebfcac83b70fe44bca0449c.png');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (6, 'https://yt3.ggpht.com/a-/AAuE7mBU4Y4HyrFGiqab7qZyP2MdYv_Hn16S21lmDQ=s900-mo-c-c0xffffffff-rj-k-no');


INSERT INTO METAL.LABELS (ID, "NAME", WEBSITE, COUNTRY_ID, IMAGE_ID) 
	VALUES (1, 'Inverse Records', 'http://www.inverse.fi/', 2, 2);
INSERT INTO METAL.LABELS (ID, "NAME", WEBSITE, COUNTRY_ID, IMAGE_ID) 
	VALUES (2, 'Nuclear Blast Records', 'https://www.nuclearblast.de/en/', 5, 6);



INSERT INTO METAL.BANDS (ID, "NAME", GENRE_ID, WEBSITE, LABEL_ID, COUNTRY_ID, IMAGE_ID) 
	VALUES (1, 'Descend', 1, 'https://www.facebook.com/DescendOfficial', 1, 1, 1);
INSERT INTO METAL.BANDS (ID, "NAME", GENRE_ID, WEBSITE, LABEL_ID, COUNTRY_ID, IMAGE_ID) 
	VALUES (2, 'Symphony X', 7, 'http://www.symphonyx.com', 2, 4, 5);


INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (1, 'CD');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (2, 'Deluxe/Digipack');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (3, 'Vinyl/LP');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (4, 'Package/BoxSet');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (5, 'Digital Download');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (6, 'T-Shirt');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (7, 'Girlie');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (8, 'Jacket/Hoodie');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (9, 'Longsleeve');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (10, 'Girlie Longsleeve');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (11, 'Caps & Hats');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (12, 'Patches, Pins & Buttons');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (13, 'Flags & Posters');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (14, 'Bags & Purses');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (15, 'Mugs & Glases');
INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (16, 'Others');


INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (1, 'XS');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (2, 'S');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (3, 'M');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (4, 'L');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (5, 'XL');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (6, 'XXL');
INSERT INTO METAL.SIZES (ID, "SIZE") 
	VALUES (7, 'N/A');


INSERT INTO METAL.ITEMS (ID, "NAME", PRICE, STOCK, CATEGORY_ID, IMAGE_ID, BAND_ID, GENRE_ID, LABEL_ID) 
	VALUES (1, 'Descend - Wither CD', 12.99, 25, 1, 3, 1, 1, 1);
INSERT INTO METAL.ITEMS (ID, "NAME", PRICE, STOCK, CATEGORY_ID, IMAGE_ID, BAND_ID, GENRE_ID, LABEL_ID) 
	VALUES (2, 'Symphony X - Underworld T-Shirt', 14.99, 31, 6, 4, 2, 7, 2);

INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (1, 2, 1, 10);
INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (2, 2, 2, 8);
INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (3, 2, 3, 8);
INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (4, 2, 4, 8);
INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (5, 2, 5, 0);
INSERT INTO METAL.CLOTHING_SIZE_STOCKS (ID, ITEM_ID, SIZE_ID, STOCK) 
	VALUES (6, 2, 6, 5);