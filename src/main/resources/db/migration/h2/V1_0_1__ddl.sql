CREATE TABLE "user" (
    id uuid NOT NULL PRIMARY KEY,
    username character varying NOT NULL,
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
	level integer
);
