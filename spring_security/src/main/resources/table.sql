create table users(username varchar(100)  not null primary key,password varchar(100) not null,enabled boolean not null);
create table authorities (username varchar(100)  not null,authority varchar(100)  not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

--Spring Security默认情况下需要两张表，用户表和权限表,可以参考

--org.springframework.security.core.userdetails.jdbc.users.ddl

