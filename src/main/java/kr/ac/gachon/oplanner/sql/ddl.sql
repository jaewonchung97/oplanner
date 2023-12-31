drop table lec_time;
drop table lecture;
drop table attendance;
drop table student;

CREATE TABLE student
(
    st_num      INT NOT NULL,
    st_name     VARCHAR(255),
    credit_required INT,
    credit_optional INT,
    PRIMARY KEY (st_num)
);

create table attendance
(
    st_num   int          Not Null,
    lec_name varchar(255) Not NULL,

    primary key (st_num, lec_name),
    foreign key (st_num) references student (st_num) on delete cascade
);


create table lecture
(
    lec_num        varchar(8)   NOT NULL,
    lec_name       varchar(255) not null,
    prof           varchar(255),
    credit         int,
    classification ENUM('MAJOR_REQUIRED', 'MAJOR_ELECTIVE'),

    primary key (lec_num)
);


create table lec_time
(
    lec_num    varchar(8) not null,
    start_time TIME       not null,
    end_time   TIME       not null,
    day        enum('MON', 'TUE', 'WED', 'THU', 'FRI') not null,

    primary key (lec_num, start_time, end_time, day),
    foreign key (lec_num) references lecture (lec_num) on delete cascade
);

insert into student values('202031233', '정재원');
insert into attendance values('202031233','리눅스');
insert into attendance values('202031233','데이터통신');
insert into attendance values('202031233','C++');
insert into attendance values('202031233','자료구조');
insert into attendance values('202031233','운영체제');
insert into attendance values('202031233','컴퓨터공학개론');