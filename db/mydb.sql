USE `iot_test_db`;


DROP TABLE IF EXISTS `user_log`;
DROP TABLE IF EXISTS `user_security`;
DROP TABLE IF EXISTS `foto_and_video`;
DROP TABLE IF EXISTS `blacklist`;
DROP TABLE IF EXISTS `preference_comment`;
DROP TABLE IF EXISTS `media_type`;
DROP TABLE IF EXISTS `tag`;
DROP TABLE IF EXISTS `preference`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `type_post`;
DROP TABLE IF EXISTS `user`;



-- -----------------------------------------------------
-- Table `iot_test_db`.`user`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`user` (
  `id_user` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nick_name` VARCHAR(45) NOT NULL,
  `foto` VARCHAR(255) NULL DEFAULT '0 or 1',
  `last_activity` DATETIME GENERATED ALWAYS AS (null),
  PRIMARY KEY (`id_user`),
  INDEX `CHECK` (`foto` ASC) VISIBLE,
  INDEX `foto` (`foto` ASC) VISIBLE,
  INDEX `last_login` (`last_activity` ASC) VISIBLE)
ENGINE = InnoDB;

INSERT INTO user (`id_user`,`nick_name`,`foto`) VALUES 
 (1,'vlad','pass.jpg'),
 (2,'ihor',NULL),
 (3,'vita','pass.png'),
 (4,'jummy','pass.png'),
 (5,'ranold',NULL),
 (6,'live',NULL),
 (7,'nasa','pass.jpg'),
 (8,'dog','dog.jpg'),
 (9,'mars','pass.jpg'),
 (10,'world',NULL);


-- -----------------------------------------------------
-- Table `iot_test_db`.`type_post`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`type_post` (
  `id_type_post` INT UNSIGNED NOT NULL,
  `type` VARCHAR(45) NOT NULL COMMENT 'storis or post',
  PRIMARY KEY (`id_type_post`),
  INDEX `post` (`id_type_post` ASC) INVISIBLE,
  INDEX `type` (`type` ASC) VISIBLE)
ENGINE = InnoDB;

INSERT INTO `type_post` (`id_type_post`, `type`) VALUES 
(1, 'post'),
(2, 'storis');

-- -----------------------------------------------------
-- Table `iot_test_db`.`post`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`post` (
  `id_post` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_user` INT UNSIGNED NOT NULL,
  `id_type_post` INT UNSIGNED NOT NULL,
  `date` DATETIME GENERATED ALWAYS AS (null),
  `description` TEXT(2000) NULL,
  PRIMARY KEY (`id_post`),
  INDEX `P.K_id_type_post_idx` (`id_type_post` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_type_post`
    FOREIGN KEY (`id_type_post`)
    REFERENCES `iot_test_db`.`type_post` (`id_type_post`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


INSERT INTO `post` (`id_post`,`id_user`, `id_type_post`,`description`) VALUES 
(1,8,1,'hellow world'),
(2,8,1,'you can '),
(3,8,2,NULL),
(4,5,2,NULL),
(5,3,1,'i see you'),
(6,4,2,NULL),
(7,7,1,'mars'),
(8,7,1,'sun'),
(9,7,1,'world'),
(10,10,1,'okey');

-- -----------------------------------------------------
-- Table `iot_test_db`.`user_log`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `iot_test_db`.`user_log` (
  `user_id` INT UNSIGNED NOT NULL,
  `date_event` DATETIME GENERATED ALWAYS AS (null),
  `log` VARCHAR(255) NOT NULL,
  INDEX `id_user` (`user_id` ASC) VISIBLE,
  INDEX `date` (`date_event` ASC) VISIBLE,
  INDEX `log` (`log` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user_log`
    FOREIGN KEY (`user_id`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `user_log` (`user_id`, `log`) VALUE
(1,'exit'),
(2,'exit'),
(3,'comment post'),
(4,'exit'),
(5,'exit'),
(6,'exit'),
(7,'comment post'),
(8,'exit'),
(9,'exit'),
(10, 'comment post');

CREATE TABLE IF NOT EXISTS `iot_test_db`.`blacklist` (
  `user_id` INT UNSIGNED NOT NULL,
  `locking_user_id` INT UNSIGNED NOT NULL,
  `locking_time` DATETIME GENERATED ALWAYS AS (null),
  INDEX `P.K_id_user_blacklist_idx` (`user_id` ASC) INVISIBLE,
  INDEX `P.K_id_user_locking_idx` (`locking_user_id` ASC) VISIBLE,
  INDEX `locking_user` (`locking_user_id` ASC) INVISIBLE,
  INDEX `locking_time` (`locking_time` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user_blacklist`
    FOREIGN KEY (`user_id`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_user_locking`
    FOREIGN KEY (`locking_user_id`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `blacklist` (`user_id`, `locking_user_id`) VALUES  
 (1, 2),
 (2, 3),
 (3, 1),
 (10, 1),
 (4, 2),
 (6, 4),
 (5, 2),
 (9, 10),
 (7, 7),
 (2, 9);

-- -----------------------------------------------------
-- Table `iot_test_db`.`comment`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`comment` (
  `id_comment` INT UNSIGNED NOT NULL,
  `id_answer` INT UNSIGNED NULL,
  `id_post` INT UNSIGNED NOT NULL,
  `id_user` INT UNSIGNED NOT NULL,
  `text` TEXT(1000) NOT NULL,
  PRIMARY KEY (`id_comment`),
  INDEX `answer_idx` (`id_answer` ASC) INVISIBLE,
  INDEX `us` (`id_user` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user_comment`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_comment`
    FOREIGN KEY (`id_post`)
    REFERENCES `iot_test_db`.`post` (`id_post`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_answer`
    FOREIGN KEY (`id_answer`)
    REFERENCES `iot_test_db`.`comment` (`id_comment`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `comment` (`id_comment`,`id_answer`,`id_post`,`id_user`,`text`) VALUES 
(1,NULL,7,1,'very beautiful'),
(2,NULL,8,2,'very good image'),
(3,NULL,9,3,'nice'),
(4,1,7,7,'thx'),
(5,2,8,7,'thx'),
(6,3,9,7,'you  a right'),
(7,NULL,7,2,'very big mars'),
(8,NULL,7,3,'where a you download this foto?'),
(9,8,7,7,'i don\'t say you)'),
(10,NULL,1,6,'very  good foto!');

-- -----------------------------------------------------
-- Table `iot_test_db`.`media_type`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`media_type` (
  `id_media_type` INT UNSIGNED NOT NULL,
  `media_type` VARCHAR(45) NOT NULL COMMENT 'foto or video',
  PRIMARY KEY (`id_media_type`),
  INDEX `media_type` (`media_type` ASC) INVISIBLE,
  INDEX `id_type` (`id_media_type` ASC) VISIBLE)
ENGINE = InnoDB;

INSERT INTO `media_type` (`id_media_type`, `media_type`) VALUE 
 (1,'foto'),
 (2,'video');

-- -----------------------------------------------------
-- Table `iot_test_db`.`foto_and_video`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`foto_and_video` (
  `id_foto_or_video` INT UNSIGNED NOT NULL,
  `id_post` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_media_type` INT UNSIGNED NOT NULL,
  `format` VARCHAR(255) NOT NULL,
  `url` VARCHAR(45) NOT NULL,
  INDEX `CHECK` (`format` ASC) VISIBLE,
  INDEX `P.K_id_post_foto_and_video_idx` (`id_post` ASC) VISIBLE,
  PRIMARY KEY (`id_foto_or_video`),
  INDEX `P.K_id_media_type_idx` (`id_media_type` ASC) VISIBLE,
  INDEX `format` (`format` ASC) INVISIBLE,
  INDEX `url` (`url` ASC) VISIBLE,
  CONSTRAINT `P.K_id_post_foto_and_video`
    FOREIGN KEY (`id_post`)
    REFERENCES `iot_test_db`.`post` (`id_post`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_media_type`
    FOREIGN KEY (`id_media_type`)
    REFERENCES `iot_test_db`.`media_type` (`id_media_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `foto_and_video` (`id_foto_or_video`,`id_post`,`format`,`id_media_type`,`url`) VALUES 
 (1,1,'png','1','D/content/foto'),
 (2,2,'jpg','1','D/content/foto'),
 (3,3,'png','1','D/content/foto'),
 (4,5,'avi','2','D/content/video'),
 (5,6,'mpeg','2','D/content/video'),
 (6,7,'png','1','D/content/foto'),
 (7,8,'mpeg','2','D/content/video'),
 (8,9,'jpg','1','D/content/foto'),
 (9,10,'avi','2','D/content/video'),
 (10,4,'png','1','D/content/foto');



-- -----------------------------------------------------
-- Table `iot_test_db`.`preference`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `iot_test_db`.`preference` (
  `id_user` INT UNSIGNED NOT NULL,
  `id_post` INT UNSIGNED NOT NULL,
  `date` DATETIME GENERATED ALWAYS AS (null),
  INDEX `P.K_id_post_preference_idx` (`id_post` ASC) VISIBLE,
  INDEX `P.K_id_user_preference_idx` (`id_user` ASC) INVISIBLE,
  INDEX `date_like` (`date` ASC) INVISIBLE,
  INDEX `user_preference` (`id_user` ASC) VISIBLE,
  CONSTRAINT `P.K_id_post_preference`
    FOREIGN KEY (`id_post`)
    REFERENCES `iot_test_db`.`post` (`id_post`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_user_preference`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `preference` (`id_user`,`id_post`) VALUES 
 (1,7),
 (1,8),
 (1,9),
 (2,1),
 (3,2),
 (4,3),
 (5,1),
 (5,3),
 (7,2),
 (7,4);

-- -----------------------------------------------------
-- Table `iot_test_db`.`preference_comment`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`preference_comment` (
  `id_comment` INT UNSIGNED NOT NULL,
  `id_user` INT UNSIGNED NOT NULL,
  INDEX `P.K_id_user_preference_comment_idx` (`id_user` ASC) VISIBLE,
  INDEX `P.K_id_comment_idx` (`id_comment` ASC) VISIBLE,
  INDEX `id_user` (`id_user` ASC) INVISIBLE,
  INDEX `id_comment` (`id_comment` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user_preference_comment`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_comment`
    FOREIGN KEY (`id_comment`)
    REFERENCES `iot_test_db`.`comment` (`id_comment`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `preference_comment` (`id_comment`,`id_user`) VALUES (1,7),
  (2,7),
  (3,7),
  (7,7),
  (8,2),
  (9,3),
  (4,1),
  (5,2),
  (6,3),
  (10,8);

-- -----------------------------------------------------
-- Table `iot_test_db`.`tag`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`tag` (
  `id_user` INT UNSIGNED NOT NULL,
  `id_post` INT UNSIGNED NOT NULL,
  `tag` VARCHAR(45) NOT NULL,
  INDEX `P.K_id_user_tag_idx` (`id_user` ASC) VISIBLE,
  INDEX `P.K_id_post_tag_idx` (`id_post` ASC) VISIBLE,
  INDEX `tag` (`tag` ASC) INVISIBLE,
  INDEX `user` (`id_user` ASC) VISIBLE,
  CONSTRAINT `P.K_id`
    FOREIGN KEY (`id_post`)
    REFERENCES `iot_test_db`.`post` (`id_post`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `P.K_id_user_tag`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `tag` (`id_user`,`id_post`,`tag`) VALUES (7,7,'mars'),
 (7,8,'sun'),
 (7,9,'world'),
 (8,1,'hellow world'),
 (8,2,'my friend'),
 (8,3,'my home'),
 (3,5,'my cat'),
 (4,6,'eat'),
 (5,4,'my dog'),
 (10,10,'okey');


-- -----------------------------------------------------
-- Table `iot_test_db`.`user_security`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `iot_test_db`.`user_security` (
  `id_user` INT UNSIGNED NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(100) NULL,
  PRIMARY KEY (`id_user`),
  INDEX `user_security` (`id_user` ASC) INVISIBLE,
  UNIQUE INDEX `id_user_UNIQUE` (`id_user` ASC) VISIBLE,
  INDEX `password` (`password` ASC) INVISIBLE,
  INDEX `phone` (`phone` ASC) VISIBLE,
  CONSTRAINT `P.K_id_user_security`
    FOREIGN KEY (`id_user`)
    REFERENCES `iot_test_db`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `user_security`  (`id_user`, `password`, `phone`) VALUES (1, '123', '0565545452'),
 (2, '7525', '0678234567'),
 (3, 'vita77', '0987234321'),
 (4, '123566', '0996789234'),
 (5, '0753', '0992347689'),
 (6, 'live5674', '0789768291'),
 (7, 'nasa-nasa', '0999234845'),
 (8, 'mydog', '0995934562'),
 (9, 'mars421', '0675678911'), 
 (10, 'woeld12', '0995600234');

