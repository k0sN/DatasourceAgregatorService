create table if not exists users
(
    id          varchar(255)  not null primary key,
    username    varchar(255)                                           not null,
    name        varchar(255)                                           not null,
    surname     varchar(255)                                           not null
);