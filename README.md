# SpringSecurity
 Basic Implementation of Spring Boot

# Schema 
create table USERS (
  ID           BIGINT not null,
  NAME         VARCHAR(36) not null,
  PASSWORD VARCHAR(128) not null,
  Email  VARCHAR(50) not null,
  ENABLED           BIT not null  
);

alter table USERS add constraint USERS_PK primary key (Email);
alter table USERS add constraint USERS_UK unique (NAME);

create table ROLES
(
  ID   BIGINT not null,
  ROLE_NAME VARCHAR(30) not null
) ;
--  
alter table ROLES add constraint ROLES_PK primary key (ID);
alter table ROLES add constraint ROLES_UK unique (ROLE_NAME);

create table USER_ROLES
(
  USER_ROLE_ID      BIGINT not null,
  Email  VARCHAR(50) not null,
  ROLE_ID BIGINT not null
);
--  
alter table USER_ROLES add constraint USER_ROLE_PK primary key (USER_ROLE_ID);
alter table USER_ROLES add constraint USER_ROLE_UK unique (Email, ROLE_ID);

alter table USER_ROLES add constraint USER_ROLE_FK foreign key (Email) references USERS (Email);

alter table USER_ROLES add constraint USER_ROLE_FK2 foreign key (ROLE_ID) references ROLES (ID);


ALTER TABLE USERS ALTER ENABLED SET DEFAULT 0;

ALTER TABLE USER_ROLES ALTER ROLE_ID SET DEFAULT 3;


# data

insert into Users (ID, NAME, PASSWORD, ENABLED,email)
values (2, 'user', '$2a$10$nu6muyG0hwGzkKJSa5bYrOSh.8zKXIcgDF413f3KvdonLyweVWYSm', 1, 'user@gmail.com');

insert into Users (ID, NAME, PASSWORD, ENABLED,email)
values (1, 'admin', '$2a$10$nu6muyG0hwGzkKJSa5bYrOSh.8zKXIcgDF413f3KvdonLyweVWYSm', 1,'admin@gmail.com');

insert into Users (ID, NAME, PASSWORD, ENABLED,email)
values (3, 'viewer', '$2a$10$nu6muyG0hwGzkKJSa5bYrOSh.8zKXIcgDF413f3KvdonLyweVWYSm', 1,'viewer@gmail.com');


insert into roles (ID, ROLE_NAME)
values (1, 'ADMIN');

insert into roles (ID, ROLE_NAME)
values (2, 'USER');

insert into roles (ID, ROLE_NAME)
values (3, 'VIEWER');




insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (1, 'admin@gmail.com', 1);

insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (4, 'admin@gmail.com', 2);

insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (5, 'admin@gmail.com', 3);

insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (2, 'user@gmail.com', 2);

insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (6, 'user@gmail.com', 3);

insert into user_roles (USER_ROLE_ID, EMAIL, ROLE_ID)
values (3, 'viewer@gmail.com', 3);

