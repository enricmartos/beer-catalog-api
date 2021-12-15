create database if not exists beer_catalog;

create table if not exists beer_catalog.beer_type
(
    id   bigint auto_increment
    primary key,
    name varchar(100) not null
    )
    charset = utf8;

create table if not exists beer_catalog.manufacturer
(
    id          bigint auto_increment
    primary key,
    name        varchar(100) not null,
    nationality varchar(100) not null
    )
    charset = utf8;

create table if not exists beer_catalog.beer
(
    id              bigint auto_increment
        primary key,
    name            varchar(100) not null,
    description     varchar(100) not null,
    graduation      float        not null,
    beer_type_id    bigint       not null,
    manufacturer_id bigint       not null,
    constraint beer_FK
        foreign key (beer_type_id) references beer_type (id),
    constraint beer_FK_1
        foreign key (manufacturer_id) references manufacturer (id)
)
    charset = utf8;



