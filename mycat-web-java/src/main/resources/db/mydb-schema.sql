CREATE TABLE USER(
  Id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  NAME varchar(255) NOT NULL,
  PASSWORD varchar(8) NOT NULL,
  ROLES varchar(128),
  ENABLED INT
);
CREATE TABLE account (
id BIGINT NOT NULL,
username VARCHAR(255) NULL,
password VARCHAR(255) NULL,
PRIMARY KEY (id)
);