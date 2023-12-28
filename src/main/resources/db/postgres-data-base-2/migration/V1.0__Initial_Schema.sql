create table if not exists ldap_users
(
    ldap_id         varchar(255)  not null primary key,
    ldap_login      varchar(255)                                           not null,
    name            varchar(255)                                           not null,
    surname         varchar(255)                                           not null
);