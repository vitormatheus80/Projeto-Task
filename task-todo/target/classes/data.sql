drop table if exists task;

create table task(
    id int auto_increment primary key,
    name varchar(250) not null,
    description varchar(250) not null
);

insert into task(name, description) values
    ('name1','Primeira tarefa'),
    ('name2','Segunda tarefa'),
    ('name3','Terceira tarefa');