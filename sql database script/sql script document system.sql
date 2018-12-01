drop table if exists revisions;
drop table if exists sharedDocs;
drop table if exists interests;
drop table if exists documents;
drop table if exists users;
drop table if exists complaints;
drop table if exists tabooList;
drop table if exists tabooSuggestions;
drop table if exists application;

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
    content text,
    FOREIGN KEY (author) REFERENCES users(userName),
    FOREIGN KEY (docID) REFERENCES documents(docID)
);

CREATE TABLE tabooList (
    tabooWord varchar(20) NOT NULL
);

CREATE TABLE tabooSuggestions(
    userName varchar(20) NOT NULL,
    tabooWord varchar(20) NOT NULL
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
insert into users value("Peter", "password", "Peter", "Phung", 2);
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

insert into documents value(1,"Jon","My Shopping List","World\nWar\nThree",0,1,"2011-08-12",0);
insert into documents value(2,"Jon","My Hello List","World\nGood\nThree",0,1,"2011-08-12",0);
insert into documents value(3,"Jon","My Good Job List","MAN\nWar\nThree",0,1,"2011-08-12",0);

insert into documents value(4,"Jon","The Jefferson's","Walter\nAccount\n112233",0,2,"2011-08-12",0);
insert into documents value(5,"Jon","Good list","World\nGood\nThree",0,3,"2011-08-12",0);
insert into documents value(6,"Jon","Food Bank","Pizza\nSoda\nChips",0,2,"2011-08-12",0);
