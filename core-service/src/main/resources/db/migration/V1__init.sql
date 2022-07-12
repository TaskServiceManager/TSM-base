create table tasks
(
        id                          bigserial primary key,
        title                       varchar(100) not null,
        description                 varchar(2000) not null,
        owner_id                    bigint not null,
        owner_name                  varchar(100) not null,
        status                      varchar(25) not null,

        completed_at                timestamp,
        created_at                  timestamp default current_timestamp,
        updated_at                  timestamp default current_timestamp
);

create table executors(
        id                          bigint primary key,
        name                        varchar(100) not null
);

create table tasks_executors(
        task_id                     bigint not null,
        executor_id                 bigint not null,

        PRIMARY KEY (task_id,executor_id),
        FOREIGN KEY (task_id)       REFERENCES tasks (id),
        FOREIGN KEY (executor_id)  REFERENCES executors (id)

);

create table comments (
        id                          bigserial primary key,
        task_id                     bigint not null,
        description                 varchar(2000),

        created_at                  timestamp default current_timestamp,
        updated_at                  timestamp default current_timestamp,
        FOREIGN KEY (task_id)       REFERENCES tasks (id)

);

create table time_points(
        id                          bigserial primary key,
        task_id                     bigint not null,
        executor_id                 bigint not null,
        status                      varchar(25),

        started_at                  timestamp default current_timestamp,
        finished_at                 timestamp default current_timestamp,
        FOREIGN KEY (task_id)       REFERENCES tasks (id),
        FOREIGN KEY (executor_id)   REFERENCES executors (id)
);

insert into tasks (title,description, owner_id, owner_name,status)
values ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 1,'Userov.UU','ACCEPTED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 4,'Userov.UU','ACCEPTED'),
       ('Ошибка включения компутера', 'Не могу включить компутер умираю уже капец блин почините', 2, 'Borisov BB', 'ACCEPTED'),
       ('Не работает', 'Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините', 2, 'Borisov BB', 'ACCEPTED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 2, 'Borisov BB','CREATED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 2, 'Borisov BB','CREATED'),
      ( 'Сломался стул','consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
      ( 'Нужно вкрутить лампочку','quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor', 2, 'Borisov BB','CREATED'),
      ( 'Помогите','aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur', 2, 'Borisov BB','CREATED'),
      ( 'ВЫНЕСЛИ ДВЕРЬ','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
      ( 'Испачкана стена','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
      ( 'Сходить на склад','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
      ( 'Сломался стул 2','consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
        ( 'Нужно вкрутить лампочку 2','quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor', 2, 'Borisov BB','CREATED'),
        ( 'Помогите 2','aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur', 2, 'Borisov BB','CREATED'),
        ( 'ВЫНЕСЛИ ДВЕРЬ 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
        ( 'Испачкана стена 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED'),
        ( 'Сходить на склад 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'Borisov BB','CREATED');

insert into executors (id, name)
values (1, 'executor1'),
       (2, 'executor2'),
       (3, 'executor3');

insert into tasks_executors (task_id, executor_id)
values (2, 1),
       (1, 2),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 2);

insert into comments (task_id, description)
values (1,'Aleksandrov.AA >> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       (2,'Ivanov I.I. >> Все чики пуки');








