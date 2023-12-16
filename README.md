***Internet CLafes***<br />
This is a JavaFX project about Internet Cafe's Management System, here's my team:<br />
1. M Sinan Abdul Syakur - 2502014381
1. Muhammad Ilyas Arsyad - 2502006215
1. Vincent Alexander Haris - 2540130081
1. Kornelia Febriany Dwi Rahayu - 2502007975
   <br />
In order to run this project, you need to run this query:<br />
CREATE DATABASE IF NOT EXISTS ooad_final;<br />
<br />
USE ooad_final;<br />
<br />
CREATE TABLE Users (<br />
userid CHAR(5) PRIMARY KEY,<br />
username VARCHAR(255) NOT NULL,<br />
userpassword VARCHAR(255) NOT NULL,<br />
userage INT NOT NULL,<br />
userrole INT NOT NULL<br />
);<br />
<br />
CREATE TABLE PC (<br />
pcid CHAR(5) PRIMARY KEY,<br />
pccondition VARCHAR(255) NOT NULL<br />
);<br />
   <br />
CREATE TABLE Report (<br />
reportid CHAR(5) PRIMARY KEY,<br />
userrole INT NOT NULL,<br />
pcid CHAR(5) NOT NULL,<br />
reportnote VARCHAR(255) NOT NULL,<br />
reportdate DATE NOT NULL,<br />
FOREIGN KEY (pcid) REFERENCES PC(pcid)<br />
);<br />
   <br />
CREATE TABLE PCBook (<br />
bookid CHAR(5) PRIMARY KEY,<br />
pcid CHAR(5) NOT NULL,<br />
userid CHAR(5) NOT NULL,<br />
bookeddate DATE NOT NULL,<br />
FOREIGN KEY (pcid) REFERENCES PC(pcid),<br />
FOREIGN KEY (userid) REFERENCES Users(userid)<br />
);<br />
   <br />
CREATE TABLE Job (<br />
jobid CHAR(5) PRIMARY KEY,<br />
userid CHAR(5) NOT NULL,<br />
pcid CHAR(5) NOT NULL,<br />
jobstatus VARCHAR(255) NOT NULL,<br />
FOREIGN KEY (userid) REFERENCES Users(userid),<br />
FOREIGN KEY (pcid) REFERENCES PC(pcid)<br />
);<br />
   <br />
CREATE TABLE TransactionHeader (<br />
transactionid CHAR(5) PRIMARY KEY,<br />
staffid CHAR(5) NOT NULL,<br />
staffname VARCHAR(255) NOT NULL,<br />
transactiontime DATE NOT NULL<br />
);<br />
CREATE TABLE TransactionDetail (<br />
transactionid CHAR(5) NOT NULL,<br />
pcid CHAR(5) NOT NULL,<br />
customername VARCHAR(255) NOT NULL,<br />
bookedtime DATE NOT NULL,<br />
FOREIGN KEY (transactionid) REFERENCES TransactionHeader(transactionid)<br />
);<br />
   <br />
INSERT INTO users (userid, username, userpassword, userage, userrole) VALUES ('US001', 'adminadmin', 'adminadmin', '20', '3');
