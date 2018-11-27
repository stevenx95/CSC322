drop table if exists revisions;
drop table if exists sharedDocs;
drop table if exists interests;
drop table if exists documents;
drop table if exists users;
drop table if exists complaints;
drop table if exists tabooWords;

CREATE TABLE complaints (
    complaintID int PRIMARY KEY,
    DocID int,
    version int,
    owner varchar(20),
    complainer varchar(20),
    message text
);


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
    userName varchar(20),
    FOREIGN KEY (userName) REFERENCES users(userName),
    interest varchar(20)
);

CREATE TABLE documents (
    docID int PRIMARY KEY,
    owner varchar(20),
    FOREIGN KEY (owner) REFERENCES users(userName),
    docName varchar(20),
    content text,
    isLocked int NOT NULL,
    restricted int NOT NULL,
    createdDate date,
    tabooFlag int
);

CREATE TABLE revisions (
    docID int(50) NOT NULL,
    version int NOT NULL,
    dateOfEdit date NOT NULL,
    author varchar(20),
    FOREIGN KEY (author) REFERENCES users(userName)
    FOREIGN KEY (docID) REFERENCES documents(docID)
);

CREATE TABLE sharedDocs (
    userName varchar(20) NOT NULL,
    docID int NOT NULL,
    FOREIGN KEY(userName) REFERENCES users(userName),
    FOREIGN KEY(docID) REFERENCES documents(docID)
);
/*Does sharedDoc table need the version number also? */


insert into users value("Jon", "password", "Jonathan", "Tran", 1);
insert into interests value("Jon", "Sleeping");
insert into interests value("Jon", "Eating");
insert into interests value("Jon", "Lifting");
insert into users value("Peter", "password", "Peter", "Phung", 1);
insert into interests value("Peter", "Filming");
insert into interests value("Peter", "Engineering");
insert into interests value("Peter", "Photography");
insert into users value("Ant", "password", "Ant", "Hony", 1);
insert into interests value("Ant", "Youtubing");
insert into interests value("Ant", "Gaming");
insert into interests value("Ant", "Writing Sketches");
insert into users value("Kelly", "password", "Kelly", "Tran", 1);
insert into interests value("Kelly", "Sleeping");
insert into interests value("Kelly", "Doing Nothing");
insert into interests value("Kelly", "Lifting");

insert into documents value("Jon","my shoping list",1111,1,"GEARS OF WAR, HALO 2, WAR Z",0,0,'2008-11-11',2);
insert into documents value("Peter","gun control?",2222,1,"nah",0,0,'2008-11-11',2);
insert into documents value("Ant","Dont do it",3333,1,"Dont fucking delete me bro.",0,0,'2008-11-11',2);
insert into documents value("Kelly","Why so serious?",4444,1,"oh hai mark. hahahahha what a funny story.",0,0,'2008-11-11',2);