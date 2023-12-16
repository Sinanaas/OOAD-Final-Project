***Internet CLafes***

This is a JavaFX project about Internet Cafe's Management System, here's my team:

1. M Sinan Abdul Syakur - 2502014381
1. Muhammad Ilyas Arsyad - 2502006215
1. Vincent Alexander Haris - 2540130081
1. Kornelia Febriany Dwi Rahayu - 2502007975

In order to run this project, you need to run this query:
```sql
CREATE DATABASE IF NOT EXISTS ooad_final;

USE ooad_final;

CREATE TABLE Users (
userid CHAR(5) PRIMARY KEY,
username VARCHAR(255) NOT NULL,
userpassword VARCHAR(255) NOT NULL,
userage INT NOT NULL,
userrole INT NOT NULL
);

CREATE TABLE PC (
pcid CHAR(5) PRIMARY KEY,
pccondition VARCHAR(255) NOT NULL
);
   
CREATE TABLE Report (
reportid CHAR(5) PRIMARY KEY,
userrole INT NOT NULL,
pcid CHAR(5) NOT NULL,
reportnote VARCHAR(255) NOT NULL,
reportdate DATE NOT NULL,
FOREIGN KEY (pcid) REFERENCES PC(pcid)
);
   
CREATE TABLE PCBook (
bookid CHAR(5) PRIMARY KEY,
pcid CHAR(5) NOT NULL,
userid CHAR(5) NOT NULL,
bookeddate DATE NOT NULL,
FOREIGN KEY (pcid) REFERENCES PC(pcid),
FOREIGN KEY (userid) REFERENCES Users(userid)
);
   
CREATE TABLE Job (
jobid CHAR(5) PRIMARY KEY,
userid CHAR(5) NOT NULL,
pcid CHAR(5) NOT NULL,
jobstatus VARCHAR(255) NOT NULL,
FOREIGN KEY (userid) REFERENCES Users(userid),
FOREIGN KEY (pcid) REFERENCES PC(pcid)
);
   
CREATE TABLE TransactionHeader (
transactionid CHAR(5) PRIMARY KEY,
staffid CHAR(5) NOT NULL,
staffname VARCHAR(255) NOT NULL,
transactiontime DATE NOT NULL
);
CREATE TABLE TransactionDetail (
transactionid CHAR(5) NOT NULL,
pcid CHAR(5) NOT NULL,
customername VARCHAR(255) NOT NULL,
bookedtime DATE NOT NULL,
FOREIGN KEY (transactionid) REFERENCES TransactionHeader(transactionid)
);
INSERT INTO users (userid, username, userpassword, userage, userrole) VALUES ('US001', 'adminadmin', 'adminadmin', '20', '3');```
