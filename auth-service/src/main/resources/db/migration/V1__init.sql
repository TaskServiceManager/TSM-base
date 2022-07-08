create table users
(
    id                                  bigserial primary key,
    username                            varchar(36) unique,
    password                            varchar(80) not null,

    first_name                          varchar(80) not null,
    last_name                           varchar(80) not null,
    middle_name                         varchar(80) not null,

    email                               varchar(50) unique,
    company                             varchar(80) not null,
    company_email                       varchar(50) ,
    work_position                       varchar(50) ,
    phone                               varchar(80) not null,

    building                            varchar(80) not null,
    office                              varchar(80) not null,

    created_at                          timestamp default current_timestamp,
    updated_at                          timestamp default current_timestamp
);

create table roles
(
    id                                  bigserial primary key,
    name                                varchar(50) not null
);

create table users_roles
(
    user_id                             bigint not null references users (id),
    role_id                             bigint not null references roles (id),
    created_at                          timestamp default current_timestamp,
    updated_at                          timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name)
values  ('ROLE_USER'),
        ('ROLE_EXECUTOR'),
        ('ROLE_MANAGER'),
        ('ROLE_ADMIN');




insert into users (username, password,first_name, last_name,middle_name ,email,company,company_email,work_position,phone,building,office)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Vasiliy','Vasiliev','Vasilievich','v@gmail.com','CTO','CTO@lalla.lu','worker','2-22-222','B','105'),
       ('executor', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Boris','Borisov','Borisovich','b@gmail.com','SUP','SUp@kuku.ku','admin','1-11-111','G','106'),
       ('manager', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Dmitriy','Dmitriev','Dmitrievich','d@gmail.com','SUP','SUp@kuku.ku','manager','3-33-333','G','105'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Alexandr','Alexandrov','Alexandrovich','a@gmail.com','SUP','SUp@kuku.ku','super admin','7-77-777','G','105');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);







