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
        FOREIGN KEY (executors_id)  REFERENCES executors (id)

);
create table comment (
        id                          bigserial primary key,
        task_id                     bigint not null,
        executor_comment            varchar(2000),

        created_at                  timestamp default current_timestamp,
        updated_at                  timestamp default current_timestamp,
        FOREIGN KEY (task_id)       REFERENCES tasks (id)

);
create table time_points(
        id                          bigserial primary key,
        task_id                     bigint not null,
        executor_id                 bigsint not null,
        status                      varchar(25),

        started_at                  timestamp default current_timestamp,
        finished_at                 timestamp default current_timestamp,
        FOREIGN KEY (process_id)    REFERENCES processes (id)
);






insert into tasks (title,description, owner_id, owner_name,status)
values ( 'всё сломалось =(',
        'плохо, плохо, плохо ничего не работает ',
        1,
        'Userov.UU',
        'expected'),
        ( 'всё сломалось =(',
        'плохо, плохо, плохо ничего не работает ',
        4,
        'Userov.UU',
        'expected')



insert into commits (order_id, executor_commit)
values (1,'Aleksandrov.AA >> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       (2,'Ivanov I.I. >> Все чики пуки');








