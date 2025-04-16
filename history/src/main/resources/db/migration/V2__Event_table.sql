create table ACCOUNT_EVENT
(
    UID            char(36)   not null,
    ACCOUNT_ID     bigint     not null,
    USER_ID        bigint     not null,
    FROM_ACCOUNT   bigint,
    CURRENCY_CODE  varchar(3) not null,
    OPERATION_CODE smallint   not null,
    AMOUNT         numeric    not null,
    CREATED        timestamp  not null
);

alter table ACCOUNT_EVENT
    add primary key (ACCOUNT_ID, UID);