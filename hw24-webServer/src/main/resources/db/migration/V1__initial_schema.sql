-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
/*
create sequence hibernate_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);
*/

drop table if exists client;
drop table if exists address;
drop table if exists phone;

create table address
(
    id       bigserial not null primary key,
    street   varchar(300)
);

create table client
(
    id         bigserial not null primary key,
    name       varchar(50),
    address_id bigint,
    foreign key (address_id) references address
);

create table phone
(
    id           bigserial not null primary key,
    number varchar(20),
    client_id    bigint,
    foreign key (client_id) references client
)

