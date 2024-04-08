drop table if exists book;
drop table if exists book_seq;

create table book (
    id bigint not null,
    created bigint,
    isbn varchar(255),
    publisher varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;

 create sequence book_seq start with 1 increment by 50 nocache;