create table reserva (
    id serial,
    cpf varchar(11) not null,
    primary key(id)
);


create table numero (
    id serial,
    ddd varchar(2) not null,
    prefixo varchar(4) not null,
    sufixo varchar(4) not null,
    codigo varchar(3) not null,
    status varchar(10) not null,
    reserva_id int,
    primary key(id),
    foreign key(reserva_id) references reserva(id)
);