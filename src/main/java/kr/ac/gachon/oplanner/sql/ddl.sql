CREATE TABLE student
(
    st_num      INT NOT NULL,
    st_name     VARCHAR(255),
    ess_credits INT,
    opt_credits INT,

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
    lec_num varchar(8) not null,
    time    int        not null check (time between 1 and 15) ,
    day     enum('MON', 'TUE', 'WED', 'THU', 'FRI'),

    primary key (lec_num, time, day),
    foreign key (lec_num) references lecture (lec_num) on delete cascade
);
