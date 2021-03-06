-- MySQL Script generated by MySQL Workbench
-- 05/30/15 23:38:04
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

DROP DATABASE IF EXISTS dbRVFF;
CREATE DATABASE dbRVFF;
USE dbRVFF;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dbRVFF
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbRVFF
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbRVFF` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `dbRVFF` ;

-- -----------------------------------------------------
-- Table `dbRVFF`.`themas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbRVFF`.`themas` ;

CREATE TABLE IF NOT EXISTS `dbRVFF`.`themas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `themas` VALUES
  (1, 'dier'),
  (2, 'animatiefilm'),
  (3, 'TVserie'),
  (4, 'kabouterfiguren'),
  (5, 'spelprogramma'),
  (6, 'film'),
  (7, 'videospellen'),
  (8, 'automerken'),
  (9,'pokemon'),
  (10,'presidenten van Amerika'),
  (11, 'eerste minister van Belgie');

-- -----------------------------------------------------
-- Table `dbRVFF`.`niveaus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbRVFF`.`niveaus` ;

CREATE TABLE IF NOT EXISTS `dbRVFF`.`niveaus` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naam` ENUM('kind', 'jeugd', 'volwassene') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `niveaus` VALUES
  (1, 'kind'),
  (2, 'jeugd'),
  (3, 'volwassene');

-- -----------------------------------------------------
-- Table `dbRVFF`.`antwoorden`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbRVFF`.`antwoorden` ;

CREATE TABLE IF NOT EXISTS `dbRVFF`.`antwoorden` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `thema_id` INT NOT NULL,
  `niveau_id` INT NOT NULL,
  `woord` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_antwoorden_themas1_idx` (`thema_id` ASC),
  INDEX `fk_antwoorden_niveaus1_idx` (`niveau_id` ASC),
  CONSTRAINT `fk_antwoorden_themas1`
    FOREIGN KEY (`thema_id`)
    REFERENCES `dbRVFF`.`themas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_antwoorden_niveaus1`
    FOREIGN KEY (`niveau_id`)
    REFERENCES `dbRVFF`.`niveaus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `antwoorden` VALUES
  (1, 1, 1, 'kikker'),
  (2, 1, 1, 'giraf'),
  (3, 1, 1, 'gorilla'),
  (4, 1, 1, 'hamster'),
  (5, 1, 1, 'kangoeroe'),
  (6, 2, 1, 'cars'),
  (7, 2, 1, 'aladdin'),
  (8, 2, 1, 'finding nemo'),
  (9, 2, 1, 'ice age'),
  (10, 3, 1, 'sesamstraat'),
  (11, 3, 1, 'barbapapa'),
  (12, 3, 1, 'mega mindy'),
  (13, 3, 1, 'tiktak'),
  (14, 4, 1, 'plop'),
  (15, 4, 1, 'kwebbel'),
  (16, 4, 1, 'lui'),
  (17, 4, 1, 'klus'),
  (18, 4, 1, 'smal'),
  (19, 4, 1, 'smul'),
  (20, 5, 2, 'rad van fortuin'),
  (21, 5, 2, 'blokken'),
  (22, 5, 2, 'valkuil'),
  (23, 6, 2, 'lord of the rings'),
  (24, 6, 2, 'avatar'),
  (25, 6, 2, 'the hobbit'),
  (26, 6, 2, 'fast and furious'),
  (27, 6, 2, 'the hunger games'),
  (28, 6, 2, 'lego the movie'),
  (29, 7, 2, 'call of duty'),
  (30, 7, 2, 'skyrim'),
  (31, 7, 2, 'need for speed'),
  (32, 7, 2, 'soldier of fortune'),
  (33, 7, 2, 'grand theft auto'),
  (34, 8, 2, 'audi'),
  (35, 8, 2, 'land rover'),
  (36, 8, 2, 'suzuki'),
  (37, 8, 2, 'toyota'),
  (38, 8, 2, 'alfa romeo'),
  (39, 9, 2, 'bulbasaur'),
  (40, 9, 2, 'charmander'),
  (41, 9, 2, 'pikachu'),
  (42, 9, 2, 'mewto'),
  (43, 9, 2, 'snorlax'),
  (44, 3, 3, 'greys anatomy'),
  (45, 3, 3, 'castle'),
  (46, 3, 3, 'grimm'),
  (47, 3, 3, 'scandal'),
  (48, 10, 3, 'george washington'),
  (49, 10, 3, 'theodore roosevelt'),
  (50, 10, 3, 'barack obama'),
  (51, 10, 3, 'thomas jefferson'),
  (52, 10, 3, 'john adams'),
  (53, 11, 3, 'charles michel'),
  (54, 11, 3, 'yves leterme'),
  (55, 11, 3, 'wilfried martens'),
  (56, 11, 3, 'leo tindermands'),
  (57, 11, 3, 'gaston eyskens'), 
  (58, 8, 3, 'subaru'), 
  (59, 8, 3, 'mitsubishi'),
  (60, 8, 3, 'aston martin'), 
  (61, 8, 3, 'rolls royce'), 
  (62, 8, 3, 'bugatti veyron');

-- -----------------------------------------------------
-- Table `dbRVFF`.`spelers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbRVFF`.`spelers` ;

CREATE TABLE IF NOT EXISTS `dbRVFF`.`spelers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `niveau_id` INT NOT NULL,
  `naam` VARCHAR(45) NULL,
  `leeftijd` INT NULL,
  `maxSaldo` INT NULL,
  `overallSaldo` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_speler_niveau1_idx` (`niveau_id` ASC),
  CONSTRAINT `fk_speler_niveau1`
    FOREIGN KEY (`niveau_id`)
    REFERENCES `dbRVFF`.`niveaus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `spelers` VALUES
  (1, '3', 'leander', '23', '10000', '34563');

-- -----------------------------------------------------
-- Table `dbRVFF`.`niveaus_has_themas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbRVFF`.`niveaus_has_themas` ;

CREATE TABLE IF NOT EXISTS `dbRVFF`.`niveaus_has_themas` (
  `niveau_id` INT NOT NULL,
  `thema_id` INT NOT NULL,
  PRIMARY KEY (`niveau_id`, `thema_id`),
  INDEX `fk_niveaus_has_themas_themas1_idx` (`thema_id` ASC),
  INDEX `fk_niveaus_has_themas_niveaus1_idx` (`niveau_id` ASC),
  CONSTRAINT `fk_niveaus_has_themas_niveaus1`
    FOREIGN KEY (`niveau_id`)
    REFERENCES `dbRVFF`.`niveaus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_niveaus_has_themas_themas1`
    FOREIGN KEY (`thema_id`)
    REFERENCES `dbRVFF`.`themas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `niveaus_has_themas` VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (2, 5),
  (2, 6),
  (2, 7),
  (2, 8),
  (2, 9),
  (3, 3),
  (3, 10),
  (3, 11),
  (3, 8);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
