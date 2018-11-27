drop table if exists revisions;
drop table if exists sharedDocs;
drop table if exists documents;
drop table if exists users;

CREATE TABLE application (
    userName varchar(20) PRIMARY KEY,
    password varchar(20) NOT NULL,
    firstName varchar(20) NOT NULL,
    lastName varchar(50) NOT NULL,
    interest0 varchar(20) NOT NULL,
    interest1 varchar(20) NOT NULL,
    interest2 varchar(20) NOT NULL,
    membershipLevel int
);

CREATE TABLE users (
    userName varchar(20) PRIMARY KEY,
    password varchar(20) NOT NULL,
    firstName varchar(20) NOT NULL,
    lastName varchar(50) NOT NULL,
    membershipLevel int
);

CREATE TABLE interests (
    userName varchar(20) NOT NULL,
    interest varchar(20)
);

CREATE TABLE documents (
    userName varchar(20) NOT NULL,
    docName varchar(20), docID int,
    version int,
    content text,
    isLocked int NOT NULL,
    resticted int NOT NULL,
    createdDate date,
    counter int,
    tabooFlag int,
    PRIMARY KEY (docID, version),
    FOREIGN KEY (userName) REFERENCES users(userName)
);

CREATE TABLE sharedDocs (
    userName varchar(20) NOT NULL,
    docID int NOT NULL,
    FOREIGN KEY(userName) REFERENCES users(userName),
    FOREIGN KEY(docID) REFERENCES documents(docID)
);
/*Does sharedDoc table need the version number also? */

CREATE TABLE revisions (userName varchar(20) NOT NULL,
docID int(50) NOT NULL, version int NOT NULL, dateOfEdit date NOT NULL,
FOREIGN KEY (userName) REFERENCES users(userName),
FOREIGN KEY (docID, version) REFERENCES documents(docID, version));

insert into users value("Jon", "password", "Jonathan", "Tran", "working out", "sleeping", "eating", 3);
insert into users value("Peter", "password", "Jonathan", "Tran", "working out", "sleeping", "eating", 1);
insert into users value("Ant", "password", "Jonathan", "Tran", "working out", "sleeping", "eating", 1);
insert into users value("Kelly", "password", "Jonathan", "Tran", "working out", "sleeping", "eating", 1);

insert into documents value("Jon","my shoping list",1111,1,"GEARS OF WAR, HALO 2, WAR Z",0,0,'2008-11-11',2);
insert into documents value("Peter","gun control?",2222,1,"nah",0,0,'2008-11-11',2);
insert into documents value("Ant","Dont do it",3333,1,"Dont fucking delete me bro.",0,0,'2008-11-11',2);
insert into documents value("Kelly","Why so serious?",4444,1,"oh hai mark. hahahahha what a funny story.",0,0,'2008-11-11',2);