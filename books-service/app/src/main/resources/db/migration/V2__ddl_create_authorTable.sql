drop table if exists author;


create table author (
    id varbinary(16) not null,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
) engine=InnoDB;

--create sequence author_seq start with 1 increment by 50 nocache;