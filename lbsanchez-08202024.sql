CREATE TABLE "Authors" (
	"Id" INT PRIMARY KEY,
	"First_Name" varchar(255),
	"Last_Name" varchar(255)
);

CREATE TABLE "Genres" (
	"Id" INT PRIMARY KEY,
	"Name" varchar(255)
);

CREATE TABLE "Books" (
	"Id" INT PRIMARY KEY,
	"Title" varchar(255),
	"Published on" date,
	"Volume" text,
	"Edition" text,
	"Pages" INT,
	"Author_Id" INT REFERENCES "Authors" ("Id"),
	"General_Id" INT REFERENCES "Genres" ("Id")
);

CREATE TABLE "Jobs" (
	"Id_Jobs" INT PRIMARY KEY,
	"Title" varchar(255)
);

CREATE TABLE "Employees" (
	"Id_Employee" INT PRIMARY KEY,
	"First_Name" varchar(255),
	"Last_Name" varchar(255),
	"Email" varchar(255),
	"Contact_No" varchar(11),
	"Salary" INT,
	"Joined_At" date,
	"Id_Jobs" INT REFERENCES "Jobs" ("Id_Jobs")
);

INSERT INTO "Jobs" VALUES (1, 'Manager'),(2, 'Product Designer'),(3, 'Software Developer');

INSERT INTO "Employees" VALUES (1, 'John', 'Doe', 'johndoe@gmail.com', '0923582168', 20000, '2016-02-27', 1);
INSERT INTO "Employees" VALUES (2, 'Jane', 'Doe', 'janedoe@gmail.com', '', 15500, '2018-03-27', 3);
INSERT INTO "Employees" VALUES (3, 'Olivia', 'Smith', 'oliviasmith@gmail.com', '094593279', 30000, '2015-06-21', 2);
INSERT INTO "Employees" VALUES (4, 'Emily', 'Wilson', 'emilywilson@gmail.com', '095604386', 16000, '2019-11-12', 3);

SELECT "Id_Employee", ("First_Name" || ' ' || "Last_Name") AS "Full_Name", "Email", "Contact_No", "Salary", "Joined_At", "Id_Jobs" FROM "Employees" ORDER BY "First_Name", "Last_Name", "Salary";



