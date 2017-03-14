Running project:
tomcat7:run-war-only -Dlardi.conf=/path/db.properties
jetty:run -Dlardi.conf=/path/db.properties


Create database in MySql by such script:

CREATE DATABASE IF NOT EXISTS phonebook;
USE phonebook;

CREATE TABLE IF NOT EXISTS clients (
  client_id        INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  client_login     VARCHAR(45) NOT NULL,
  client_pass      VARCHAR(45) NOT NULL,
  client_full_name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS phonebook (
  note_id         INT         NOT NULL AUTO_INCREMENT,
  client_id       INT         NOT NULL,
  first_name      VARCHAR(45) NOT NULL,
  second_name     VARCHAR(45) NOT NULL,
  additional_name VARCHAR(45) NOT NULL,
  mobile_number   VARCHAR(45) NOT NULL,
  home_number     VARCHAR(45) NULL,
  location        VARCHAR(45) NULL,
  email           VARCHAR(45) NULL,
  PRIMARY KEY (note_id, client_id),
  FOREIGN KEY (client_id) REFERENCES clients (client_id)
);

CREATE USER IF NOT EXISTS 'phonebook'@'localhost' IDENTIFIED BY 'phonebookPass';
GRANT ALL PRIVILEGES ON phonebook.* TO 'phonebook'@'localhost';

INSERT INTO phonebook.clients (client_id, client_login, client_pass, client_full_name) VALUES (1, 'user', 'user', 'Morris Blaze');
INSERT INTO phonebook.clients (client_id, client_login, client_pass, client_full_name) VALUES (2, 'EvansC', 'qwert', 'Evans Collin');
