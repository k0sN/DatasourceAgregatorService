create table if not exists user_table
(
    user_id        varchar(255)  not null primary key,
    user_login     varchar(255)                                           not null,
    user_name      varchar(255)                                           not null,
    user_surname   varchar(255)                                           not null
);