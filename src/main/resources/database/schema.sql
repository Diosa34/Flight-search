create type gender as enum (
    'male',
    'female'
);

create type document_type as enum (
    'passport',
    'foreign passport',
    'military ticket'
);

create table if not exists person (
    user_id serial primary key,
    name varchar(20) not null,
    patronymic varchar(20),
    surname varchar(20) not null,
    gender gender not null,
    dateOfBirth date not null,
    country varchar(20) not null,
    documentType document_type not null,
    documentNumber varchar(20) not null,
    telephone varchar(64) unique not null,
    email varchar(320) unique not null,
    login varchar(20) unique,
    passwordHash char(182) not null
);