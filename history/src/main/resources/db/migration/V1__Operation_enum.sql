create table OPERATION
(
    ID   smallint   not null primary key,
    CODE varchar(8) not null
);

insert into OPERATION(ID, CODE)
values (1, 'PUT'),
       (2, 'EXCHANGE');