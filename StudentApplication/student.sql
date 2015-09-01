drop table if exists student;
create table student (
   studID bigint unsigned not null auto_increment primary key,
    
    first_name varchar(40) not null,
    middleInitial char(1),
    last_name varchar(40) not null,
    email varchar(80),
    date_created timestamp default 0,
    date_modified timestamp default current_timestamp on update current_timestamp,
    unique index student_idx1 (last_name, first_name, middleInitial)
) engine = InnoDB;



drop table if exists course;
create table course 
(
course_ID bigint unsigned not null primary key,
course_Name varchar(40) not null,
courseStartDate DATE ,
courseEndDate   DATE ,
faculty_FirstName varchar(40) not null,
faculty_LastName varchar(40) not null,  
faculty_Email varchar(80),
date_created timestamp default 0,
date_modified timestamp default current_timestamp on update current_timestamp,
    unique index course_idx1 (course_Name)
) engine = InnoDB;




insert into student values
 (1, 'Smita', 'J', 'S' 'smita@maine.edu', null, null),
 (2,  'Smit',  'J', 'S' 'smita@maine.edu', null, null),
 (3, 'Bob', 'T', 'Steven' 'bob@maine.edu', null, null);
 

drop table if exists course;
create table course 
(
course_ID bigint unsigned not null primary key,
course_Name varchar(40) not null,
courseStartDate DATE ,
courseEndDate   DATE ,
faculty_FirstName varchar(40) not null,
faculty_LastName varchar(40) not null,  
faculty_Email varchar(80),
date_created timestamp default 0,
date_modified timestamp default current_timestamp on update current_timestamp,
    unique index course_idx1 (course_Name)
) engine = InnoDB;



insert into course values
(453, 'Machine learning' , '2015-08-30', '2015-12-30', 'John', 'Steven', 'john@maine.edu', null, null),
(454, 'ComputerNetwork' , '2015-08-30', '2015-12-30', 'James', 'Adams', 'james@maine.edu', null, null);




	
