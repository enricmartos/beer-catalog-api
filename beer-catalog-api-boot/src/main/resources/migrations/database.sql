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
    description     varchar(1000) not null,
    graduation      float        not null,
    beer_type_id    bigint       not null,
    manufacturer_id bigint       not null,
    constraint beer_FK
        foreign key (beer_type_id) references beer_type (id),
    constraint beer_FK_1
        foreign key (manufacturer_id) references manufacturer (id)
)
    charset = utf8;

insert ignore into beer_catalog.manufacturer
    (id, name, nationality)
VALUES
    (1, 'Punk API', 'North American');

insert ignore into beer_catalog.manufacturer
    (id, name, nationality)
VALUES
    (2, 'Hijos de Rivera', 'Spanish');

insert ignore into beer_catalog.manufacturer
    (id, name, nationality)
VALUES
    (3, 'Brauerei Gruppe', 'Dutch');

insert ignore into beer_catalog.beer_type
    (id, name)
VALUES
    (1, 'Lager');

insert ignore into beer_catalog.beer_type
    (id, name)
VALUES
    (2, 'Ale');

insert ignore into beer_catalog.beer
    (id, name, description, graduation, beer_type_id, manufacturer_id)
VALUES
    (1, 'Estrella Galicia', 'Bright golden beer with especially bitter malts', 4.7, 1, 2);

insert ignore into beer_catalog.beer
    (id, name, description, graduation, beer_type_id, manufacturer_id)
VALUES
    (2, 'Paulaner', 'Pleasantly malty beer with subtle notes of hops', 5.5, 2, 3);





