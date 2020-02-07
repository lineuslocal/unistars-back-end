CREATE TABLE "user" (
    id uuid NOT NULL PRIMARY KEY,
    username character varying(200) NOT NULL,
    email character varying(200),
	password character varying(200),
	fullname character varying(200),
	phonenumber character varying(20),
	gender character varying(5),
	address character varying(200),
	city character varying(50),
	job character varying(50),
	birthdate character varying(20),
	roles character varying(200),
	level character varying(20)
);


CREATE TABLE subject (
    id uuid NOT NULL PRIMARY KEY,
    name character varying NOT NULL
);

CREATE TABLE category (
    id uuid NOT NULL PRIMARY KEY,
    name character varying(200) NOT NULL,
    subject_id uuid NOT NULL REFERENCES subject (id)
);

CREATE TABLE product (
    id uuid NOT NULL PRIMARY KEY,
    name character varying(200) NOT NULL,
    category_id uuid NOT NULL REFERENCES category (id)
);

CREATE TABLE faq (
    id uuid NOT NULL PRIMARY KEY,
    title character varying(200) NOT NULL,
    content uuid NOT NULL REFERENCES category (id),
    level character varying(20),
    product_id uuid NOT NULL REFERENCES product (id),
    created_date DATE
);



