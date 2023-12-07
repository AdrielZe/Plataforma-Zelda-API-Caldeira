create table users (
    id serial primary key not null,
    idade int not null,
    nome varchar(255) not null
);

insert into users(idade,nome) values
    ('22','Iago'),
    ('13','Gabriel'),
    ('87','Maria'),
    ('54','Julia'),
    ('65','Moises'),
    ('22','Pedro'),
    ('26','Djenifer'),
    ('32','José'),
    ('75','Arthur'),
    ('23','Rosalinda'),
    ('44','thiago');