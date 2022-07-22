create table tasks
(
        id                          bigserial primary key,
        title                       varchar(100) not null,
        description                 varchar(2000) not null,
        owner_id                    bigint not null,
        chief_id                    bigint,
        status                      varchar(25) not null,


        completed_at                timestamp,
        created_at                  timestamp default current_timestamp,
        updated_at                  timestamp default current_timestamp
);




create table tasks_executors(
        task_id                     bigint not null,
        executor_id                 bigint not null,

        FOREIGN KEY (task_id)       REFERENCES tasks (id)

);

create table comments (
        id                          bigserial primary key,
        task_id                     bigint not null,
        author_id                   bigint not null,
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
        FOREIGN KEY (task_id)       REFERENCES tasks (id)

);

insert into tasks (title,description, owner_id,status)
values ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 1,'ACCEPTED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 4,'ACCEPTED'),
       ('Ошибка включения компутера', 'Не могу включить компутер умираю уже капец блин почините', 2, 'ACCEPTED'),
       ('Не работает', 'Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините', 2,  'ACCEPTED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 2, 'CREATED'),
       ( 'всё сломалось =(','плохо, плохо, плохо ничего не работает ', 2, 'CREATED'),
      ( 'Сломался стул','consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
      ( 'Нужно вкрутить лампочку','quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor', 2, 'CREATED'),
      ( 'Помогите','aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur', 2, 'CREATED'),
      ( 'ВЫНЕСЛИ ДВЕРЬ','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
      ( 'Испачкана стена','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
      ( 'Сходить на склад','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
      ( 'Сломался стул 2','consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
        ( 'Нужно вкрутить лампочку 2','quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor', 2, 'CREATED'),
        ( 'Помогите 2','aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur', 2, 'CREATED'),
        ( 'ВЫНЕСЛИ ДВЕРЬ 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
        ( 'Испачкана стена 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED'),
        ( 'Сходить на склад 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 2, 'CREATED');



insert into tasks_executors (task_id, executor_id)
values (2, 1),
       (1, 2),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 2);

insert into comments (task_id, author_id,description)
values (1,3,'>> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       (2,2,'>> Все чики пуки'),
       (4,1,'Выполнены работы по починке того, что было сломано'),
       (4,2,'Нет не выполнены работы по починке того, что было сломано'),
       (4,3,'Теперь точно выполнены');








