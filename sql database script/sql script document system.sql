drop table if exists revisions;
drop table if exists sharedDocs;
drop table if exists documents;
drop table if exists users;

CREATE TABLE users (userName varchar(20) PRIMARY KEY, password varchar(20) NOT NULL, firstName varchar(20) NOT NULL, 
lastName varchar(50) NOT NULL, interest0 varchar(20) NOT NULL, interest1 varchar(20) NOT NULL, interest2 varchar(20) NOT NULL,
membershipLevel int(10));

CREATE TABLE documents (userName varchar(20) NOT NULL,docName varchar(20), docID int(50), 
version int(50), content text, public int(1) NOT NULL, resticted int(1) NOT NULL, counter int(32),
PRIMARY KEY (docID, version),
FOREIGN KEY (userName) REFERENCES users(userName));    

CREATE TABLE sharedDocs (userName varchar(20) NOT NULL, docID int(50) NOT NULL,
FOREIGN KEY(userName) REFERENCES users(userName),
FOREIGN KEY(docID) REFERENCES documents(docID));
/*Does sharedDoc table need the version number also? */

CREATE TABLE revisions (userName varchar(20) NOT NULL, 
docID int(50) NOT NULL, version int(50) NOT NULL, dateOfEdit date NOT NULL,
FOREIGN KEY (userName) REFERENCES users(userName),
FOREIGN KEY (docID, version) REFERENCES documents(docID, version));