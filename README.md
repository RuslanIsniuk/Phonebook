Running project:
package tomcat7:run-war -Dlardi.conf=/path/db.properties
jetty:run -Dlardi.conf=/path/db.properties
login:MorrisB
pass:12345

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

INSERT INTO `clients` (`client_id`,`client_login`,`client_pass`,`client_full_name`) VALUES (1,'MorrisB','12345','Morris Blaze');
INSERT INTO `clients` (`client_id`,`client_login`,`client_pass`,`client_full_name`) VALUES (2,'EvansC','qwert','Evans Collin');

INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (1,1,'Максим','Соменков','Пашкевич','+380981485674','','','');
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (2,1,'Артем','Гайовый','Григорович','+380968534796','+380446587685','м.Київ, Проспект Перемоги 25Б','Guy@gmail.com');
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (3,1,'Діана','Чумаковська','Миколаївна','+380970413574','+380442541297','м. Київ, вул. Васильківська 30','chumak@gmail.com');
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (4,1,'Максим','Чумаченко','Жиловенко','+380639458458',NULL,NULL,NULL);
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (5,2,'Денис','Вийт','Якович','+380551665195',NULL,NULL,NULL);
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (6,2,'Ярослав','Дзюба','Лукич','+380961212454',NULL,NULL,NULL);
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (7,1,'Софія','Гойко','Іванівна','+380500146578','','','');
INSERT INTO `phonebook` (`note_id`,`client_id`,`first_name`,`second_name`,`additional_name`,`mobile_number`,`home_number`,`location`,`email`) VALUES (8,1,'Владислав','Саенко','Григорович','+380980234185','+380441257465','м. Львів вул. Прохудіна 8','Saenko@gmail.com');
