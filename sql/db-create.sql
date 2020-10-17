-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema beauty_salon
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema beauty_salon
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `beauty_salon` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `beauty_salon`;

-- -----------------------------------------------------
-- Table `beauty_salon`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`role`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`role`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `rolename` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`user`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`user`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(45)  NOT NULL,
    `last_name`  VARCHAR(45)  NOT NULL,
    `email`      VARCHAR(255) NOT NULL,
    `password`   VARCHAR(32)  NOT NULL,
    `role_id`    INT          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_role1`
        FOREIGN KEY (`role_id`)
            REFERENCES `beauty_salon`.`role` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 13
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`service`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`service`
(
    `id`           INT            NOT NULL AUTO_INCREMENT,
    `service_name` VARCHAR(60)    NOT NULL,
    `price`        DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 15
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`booking`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`booking`
(
    `id`              INT                                NOT NULL AUTO_INCREMENT,
    `service_id`      INT                                NOT NULL,
    `client_id`       INT                                NOT NULL,
    `mark`            INT                                NULL     DEFAULT NULL,
    `progress_status` ENUM ('inProgress', 'done')        NOT NULL DEFAULT 'inProgress',
    `payment_status`  ENUM ('notConfirmed', 'confirmed') NOT NULL DEFAULT 'notConfirmed',
    `comment`         VARCHAR(255)                       NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_booking_service1_idx` (`service_id` ASC) VISIBLE,
    INDEX `fk_booking_person1_idx` (`client_id` ASC) VISIBLE,
    CONSTRAINT `fk_booking_person1`
        FOREIGN KEY (`client_id`)
            REFERENCES `beauty_salon`.`user` (`id`),
    CONSTRAINT `fk_booking_service1`
        FOREIGN KEY (`service_id`)
            REFERENCES `beauty_salon`.`service` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 14
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`calendar`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`calendar`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`calendar`
(
    `id`   INT  NOT NULL AUTO_INCREMENT,
    `date` DATE NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `date_UNIQUE` (`date` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 8
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`master_has_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`master_has_service`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`master_has_service`
(
    `id`         INT NOT NULL AUTO_INCREMENT,
    `user_id`    INT NOT NULL,
    `service_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_user_has_service_service1_idx` (`service_id` ASC) VISIBLE,
    INDEX `fk_user_has_service_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_has_service_service1`
        FOREIGN KEY (`service_id`)
            REFERENCES `beauty_salon`.`service` (`id`),
    CONSTRAINT `fk_user_has_service_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `beauty_salon`.`user` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 8
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`master_mark`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`master_mark`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`master_mark`
(
    `id`        INT    NOT NULL AUTO_INCREMENT,
    `master_id` INT    NOT NULL,
    `mark`      DOUBLE NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `master_id_UNIQUE` (`master_id` ASC) VISIBLE,
    INDEX `fk_master_mark_user1_idx` (`master_id` ASC) VISIBLE,
    CONSTRAINT `fk_master_mark_user1`
        FOREIGN KEY (`master_id`)
            REFERENCES `beauty_salon`.`user` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`timeslot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`timeslot`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`timeslot`
(
    `id`         INT  NOT NULL AUTO_INCREMENT,
    `start_time` TIME NOT NULL,
    `end_time`   TIME NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 41
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beauty_salon`.`timetable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon`.`timetable`;

CREATE TABLE IF NOT EXISTS `beauty_salon`.`timetable`
(
    `id`          INT NOT NULL AUTO_INCREMENT,
    `calendar_id` INT NOT NULL,
    `timeslot_id` INT NOT NULL,
    `master_id`   INT NOT NULL,
    `booking_id`  INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `booking_id_UNIQUE` (`booking_id` ASC) VISIBLE,
    INDEX `fk_timetable_timeslot1_idx` (`timeslot_id` ASC) VISIBLE,
    INDEX `fk_timetable_user1_idx` (`master_id` ASC) VISIBLE,
    INDEX `fk_timetable_booking1_idx` (`booking_id` ASC) VISIBLE,
    INDEX `fk_timetable_calendar1_idx` (`calendar_id` ASC) VISIBLE,
    CONSTRAINT `fk_timetable_booking1`
        FOREIGN KEY (`booking_id`)
            REFERENCES `beauty_salon`.`booking` (`id`),
    CONSTRAINT `fk_timetable_calendar1`
        FOREIGN KEY (`calendar_id`)
            REFERENCES `beauty_salon`.`calendar` (`id`),
    CONSTRAINT `fk_timetable_timeslot1`
        FOREIGN KEY (`timeslot_id`)
            REFERENCES `beauty_salon`.`timeslot` (`id`),
    CONSTRAINT `fk_timetable_user1`
        FOREIGN KEY (`master_id`)
            REFERENCES `beauty_salon`.`user` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 54
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
