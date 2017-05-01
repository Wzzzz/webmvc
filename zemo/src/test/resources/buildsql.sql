DROP TABLE IF EXISTS `user`;
CREATE TABLE `demo`.`user` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `message` TEXT(1024) NULL,
  `datetime` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;