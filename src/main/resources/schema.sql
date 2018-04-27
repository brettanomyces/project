CREATE TABLE if not exists users (
  id       serial PRIMARY KEY,
  username varchar(256) NOT NULL,
  password varchar(256),
  email    varchar(256) NOT NULL
);