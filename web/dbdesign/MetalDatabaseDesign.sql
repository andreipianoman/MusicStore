drop table METAL.ITEMS;
drop table METAL.BANDS;
drop table METAL.LABELS;
drop table METAL.CATEGORIES;
drop table METAL.COUNTRIES;
drop table METAL.GENRES;
drop table METAL.IMAGES;
drop table METAL.USERS;
drop table METAL.ROLES;
drop table METAL.SHIRT_SIZES;

create table METAL.BANDS (ID integer primary key, NAME varchar(100), GENRE_ID integer, WEBSITE varchar(10000), LABEL_ID integer, COUNTRY_ID integer, IMAGE_ID integer);
create table METAL.CATEGORIES(ID integer primary key, NAME varchar(100));
create table METAL.SHIRT_SIZES(ID integer primary key, SIZE varchar(10));
create table METAL.COUNTRIES(ID integer primary key, NAME varchar(100));
create table METAL.GENRES(ID integer primary key, NAME varchar(100));
create table METAL.IMAGES(ID integer primary key, ADDRESS varchar(10000));
create table METAL.ITEMS(ID integer primary key, NAME varchar(100), PRICE double, STOCK integer, CATEGORY_ID integer, IMAGE_ID integer, BAND_ID integer, GENRE_ID integer, LABEL_ID integer, SIZE_ID integer);
create table METAL.LABELS (ID integer primary key, NAME varchar(100), WEBSITE varchar(10000), COUNTRY_ID integer, IMAGE_ID integer);
create table METAL.ROLES(ID  integer primary key, NAME varchar(100));
create table METAL.USERS(ID  integer primary key, USERNAME varchar(100), PASSWORD varchar(25), ROLE_ID integer);

ALTER TABLE METAL.BANDS ADD FOREIGN KEY (LABEL_ID) REFERENCES METAL.LABELS (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (GENRE_ID) REFERENCES METAL.GENRES (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (COUNTRY_ID) REFERENCES METAL.COUNTRIES (ID);
ALTER TABLE METAL.BANDS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (CATEGORY_ID) REFERENCES METAL.CATEGORIES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (BAND_ID) REFERENCES METAL.BANDS (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (GENRE_ID) REFERENCES METAL.GENRES (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (LABEL_ID) REFERENCES METAL.LABELS (ID);
ALTER TABLE METAL.ITEMS ADD FOREIGN KEY (SIZE_ID) REFERENCES METAL.SHIRT_SIZES (ID);
ALTER TABLE METAL.LABELS ADD FOREIGN KEY (COUNTRY_ID) REFERENCES METAL.COUNTRIES (ID);
ALTER TABLE METAL.LABELS ADD FOREIGN KEY (IMAGE_ID) REFERENCES METAL.IMAGES (ID);
ALTER TABLE METAL.USERS ADD FOREIGN KEY (ROLE_ID) REFERENCES METAL.ROLES (ID);

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

INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (1, 'Sweden');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (2, 'Finland');
INSERT INTO METAL.COUNTRIES (ID, "NAME") 
	VALUES (3, 'Netherlands');

INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (1, 'https://www.metal-archives.com/images/8/7/6/8/87685_logo.jpg?4307');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (2, 'https://www.metal-archives.com/images/1/5/1/7/15175_label.jpg?4829');
INSERT INTO METAL.IMAGES (ID, ADDRESS) 
	VALUES (3, 'https://mir-s3-cdn-cf.behance.net/project_modules/disp/8480b510017273.5627a4c489f2f.jpg');

INSERT INTO METAL.LABELS (ID, "NAME", WEBSITE, COUNTRY_ID, IMAGE_ID) 
	VALUES (1, 'Inverse Records', 'http://www.inverse.fi/', 2, 2);

INSERT INTO METAL.BANDS (ID, "NAME", GENRE_ID, WEBSITE, LABEL_ID, COUNTRY_ID, IMAGE_ID) 
	VALUES (1, 'Descend', 1, 'https://www.facebook.com/DescendOfficial', 1, 1, 1);

INSERT INTO METAL.CATEGORIES (ID, "NAME") 
	VALUES (1, 'CD');

INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (1, 'XS');
INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (2, 'S');
INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (3, 'M');
INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (4, 'L');
INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (5, 'XL');
INSERT INTO METAL.SHIRT_SIZES (ID, "SIZE") 
	VALUES (6, 'XXL');


INSERT INTO METAL.ITEMS (ID, "NAME", PRICE, STOCK, CATEGORY_ID, IMAGE_ID, BAND_ID, GENRE_ID, LABEL_ID) 
	VALUES (1, 'Descend - Wither CD', 12.99, 25, 1, 3, 1, 1, 1);