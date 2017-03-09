-- MySQL Script generated by MySQL Workbench
-- 03/08/17 20:18:37
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema web_service_phonebook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema web_service_phonebook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_service_phonebook` DEFAULT CHARACTER SET utf8 ;
USE `web_service_phonebook` ;

-- -----------------------------------------------------
-- Table `web_service_phonebook`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_service_phonebook`.`clients` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `client_login` VARCHAR(45) NOT NULL,
  `client_pass` VARCHAR(45) NOT NULL,
  `client_full_name` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `client_login_UNIQUE` (`client_login` ASC),
  UNIQUE INDEX `client_full_name_UNIQUE` (`client_full_name` ASC),
  PRIMARY KEY (`client_id`),
  UNIQUE INDEX `client_id_UNIQUE` (`client_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `web_service_phonebook`.`phonebook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_service_phonebook`.`phonebook` (
  `client_id` INT NOT NULL,
  `note_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `second_name` VARCHAR(45) NOT NULL,
  `additional_name` VARCHAR(45) NOT NULL,
  `mobile_number` VARCHAR(45) NOT NULL,
  `home_number` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE INDEX `note_id_UNIQUE` (`note_id` ASC),
  CONSTRAINT `fk_phonebook_clients`
    FOREIGN KEY (`client_id`)
    REFERENCES `web_service_phonebook`.`clients` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
