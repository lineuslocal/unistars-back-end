CREATE TABLE "user" (
    id uuid NOT NULL PRIMARY KEY,
    email character varying(200) NOT NULL,
	password character varying(200) NOT NULL,
	fullName character varying(200) NOT NULL,
	phoneNumber character varying(20) NOT NULL,
	gender character varying(5) NOT NULL,
	zipCode character varying(20) NOT NULL,
	address character varying(200) NOT NULL,
	city character varying(50) NOT NULL,
	birthdate character varying(20) NOT NULL,
	roles character varying(200) NOT NULL,
	level integer
);
