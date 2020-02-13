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
    name character varying NOT NULL,
    note text
);

CREATE TABLE category (
    id uuid NOT NULL PRIMARY KEY,
    name character varying(200) NOT NULL,
    subject_id uuid NOT NULL REFERENCES subject (id),
    note text
);

CREATE TABLE product (
    id uuid NOT NULL PRIMARY KEY,
    name character varying(200) NOT NULL,
    category_id uuid NOT NULL REFERENCES category (id),
    note text
);

CREATE TABLE faq (
    id uuid NOT NULL PRIMARY KEY,
    title character varying(200) NOT NULL,
    content text,
    status boolean,
    level character varying(20),
    product_id uuid NOT NULL REFERENCES product (id),
    created_date DATE
);

CREATE TABLE faq_image (
    id uuid NOT NULL PRIMARY KEY,
    file_name character varying(4096),
    file_type character varying(256),
    faq_id uuid NOT NULL REFERENCES faq (id),
    data BLOB 
    );

CREATE TABLE keyword (
    id uuid NOT NULL PRIMARY KEY,
    keyword character varying(200) NOT NULL,
    note text,
    created_date DATE
);

CREATE TABLE faq_keyword (
	faq_id uuid NOT NULL REFERENCES faq (id),
	keyword_id uuid NOT NULL REFERENCES keyword (id)
);


