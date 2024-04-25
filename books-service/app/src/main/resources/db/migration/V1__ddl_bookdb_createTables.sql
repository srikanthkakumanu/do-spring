drop table if exists book;


create table book (
    id varbinary(16) not null,
    created timestamp,
    isbn varchar(255),
    publisher varchar(255),
    title varchar(255),
    author_id varbinary(16),
    primary key (id)
) engine=InnoDB;

--create sequence book_seq start with 1 increment by 50 nocache;