drop table if exists studAccount_studRole;
drop table if exists studROle;
drop table if exists studAccount;

create table studAccount (
    studID int unsigned not null auto_increment primary key,
    username varchar(50) unique not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(64), -- varchar(64) to accommodate SHA-256 hashes
    marketing_ok boolean not null,
    accept_terms boolean not null,
    enabled boolean not null,
    date_created timestamp default 0,
    date_modified timestamp default current_timestamp on update current_timestamp,
    unique index studAccount_idx_1 (username),
    unique index studAccount_idx_2 (email)
) engine = InnoDb;

create table studRole (
    studID tinyint unsigned not null auto_increment primary key,
    name varchar(50) not null unique
) engine = InnoDb;


create table studAccount_studRole (
studID int unsigned not null auto_increment primary key,
studAccount_id int unsigned not null,
studRole_id tinyint unsigned not null,
foreign key (studaccount_id) references studAccount (studID),
foreign key (studRole_id) references studRole(studID),
unique index studAccount_studRole_idx_1 (studAccount_id, studROle_id)
) engine = InnoDb;



create table courseAct (
    courseID int unsigned not null auto_increment primary key,
    coursename varchar(50) unique not null,
    facultyFirstName varchar(50) not null,
    facultyLastName varchar(50) not null,
   startDate varchar(10) not null,
endDate varchar(10) not null,
    
    date_created timestamp default 0,
    date_modified timestamp default current_timestamp on update current_timestamp,
    unique index courseAct_idx_1 (coursename)
    
) engine = InnoDb;

