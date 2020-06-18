create table advertisement
(
  id              int          not null primary key,
  description     varchar(255) null default null,
  expiration_date timestamp    null default null,
  price           double       null default null,
  start_date      timestamp    null default null,
  title           varchar(255) null default null,
  category_id     int          null default null,
  city_id         int          null default null,
  university_id   int          null default null,
  user_id         int          null default null
);

create table category
(
  id   int          not null primary key,
  name varchar(255) null default null
)
;
create table city
(
  id   int          not null primary key,
  name varchar(255) null default null
);

create table hibernate_sequence
(
  next_val int null default null
);

insert into hibernate_sequence (next_val) values (1);

create table image
(
  id               int          not null,
  alt              varchar(255) null default null,
  path             varchar(255) null default null,
  src              varchar(255) null default null,
  timestamp        timestamp    null default null,
  advertisement_id int          null default null
)
;
create table role
(
  id   int          not null primary key,
  role varchar(255) null default null
)
;
create table role_user
(
  role_id int not null,
  user_id int not null
)
;
create table university
(
  id         int          not null,
  name       varchar(255) null default null,
  short_name varchar(255) null default null,
  city_id    int          null default null
)
;
create table user
(
  id       int          not null,
  active   int          not null,
  email    varchar(255) null default null,
  name     varchar(255) null default null,
  password varchar(255) null default null
);

ALTER TABLE advertisement ADD FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE advertisement ADD FOREIGN KEY (city_id) REFERENCES city(id);
ALTER TABLE advertisement ADD FOREIGN KEY (university_id) REFERENCES university(id);
ALTER TABLE advertisement ADD FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLe image ADD FOREIGN KEY (advertisement_id) REFERENCES advertisement(id);

ALTER TABLe role_user ADD FOREIGN KEY (role_id) REFERENCES role(id);
ALTER TABLe role_user ADD FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLe university ADD FOREIGN KEY (city_id) REFERENCES city(id);