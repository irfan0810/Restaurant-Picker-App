-- -----------------------------------------------------
-- Schema restaurant-picker-02
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `restaurant-picker-02`;

CREATE SCHEMA `restaurant-picker-02`;
USE `restaurant-picker-02` ;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(100) DEFAULT NULL,
  `first_name` VARCHAR(100) DEFAULT NULL,
  `last_name` VARCHAR(100) DEFAULT NULL,
  `username` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(150) DEFAULT NULL,
  `profile_image_url` VARCHAR(255) DEFAULT NULL,
  `last_login_date` DATETIME DEFAULT NULL,
  `last_login_date_display` DATETIME DEFAULT NULL,
  `join_date` DATETIME DEFAULT NULL,
  `role` VARCHAR(50) DEFAULT NULL COMMENT 'e.g. ROLE_USER or ROLE_ADMIN',
   `authorities` BLOB DEFAULT NULL COMMENT 'Serialized Java String[] of authorities',
  `is_active` BOOLEAN DEFAULT TRUE,
  `is_not_locked` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `restaurant-picker-02`.`session` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `link` VARCHAR(255) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `image_url` VARCHAR(255) DEFAULT NULL,
  `active` BIT DEFAULT 1,
  `date_created` DATETIME(6) DEFAULT NULL,
  `last_updated` DATETIME(6) DEFAULT NULL,
  `result` VARCHAR(255) DEFAULT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) 
ENGINE=InnoDB
AUTO_INCREMENT = 1;


CREATE TABLE IF NOT EXISTS `restaurant-picker-02`.`session_restaurant` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `active` BIT DEFAULT 1,
  `date_created` DATETIME(6) DEFAULT NULL,
  `last_updated` DATETIME(6) DEFAULT NULL,
  `session_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_session` (`session_id`),
  CONSTRAINT `fk_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) 
ENGINE=InnoDB
AUTO_INCREMENT = 1;

INSERT INTO `restaurant-picker-02`.`user` 
	(`id`,`user_id`,`first_name`,`last_name`,`username`,`password`,`email`,`profile_image_url`,`last_login_date`,`last_login_date_display`,`join_date`,`role`,`authorities`,`is_active`,`is_not_locked`) 
VALUES 
	(1,'4577427965','John','Doe','john','$2a$10$UhWfZHetQfVQVOw33bXOzOWn1wATDq4zPUomF7gNjLrvM41q.ynGq','email@john.com',NULL,'2025-10-24 17:06:28','2025-10-24 16:55:44','2025-10-24 13:30:19','ROLE_SUPER_ADMIN',?,1,1);
INSERT INTO `restaurant-picker-02`.`user` 
	(`id`,`user_id`,`first_name`,`last_name`,`username`,`password`,`email`,`profile_image_url`,`last_login_date`,`last_login_date_display`,`join_date`,`role`,`authorities`,`is_active`,`is_not_locked`) 
VALUES 
	(2,'3191129631','susan','Doe','susan','$2a$10$FgHxkrcSn.wIAu0LfR42iOau6qrdYvX986gzu0NwFU8sbQJgpmsiK','email@susan.com',NULL,'2025-10-24 18:21:25','2025-10-24 18:09:42','2025-10-24 17:09:46','ROLE_SUPER_ADMIN',?,1,1);   


INSERT INTO `restaurant-picker-02`.`session`
    (`link`, `name`, `description`, `image_url`, `active`, `date_created`, `last_updated`, `user_id`)
VALUES
    ('http://localhost:4200/link/ABC123', 'Team Lunch Friday', 'Team lunch to decide on where to eat this Friday', 'assets/images/fish.png', 1, NOW(), NOW(), 1),

    ('http://localhost:4200/link/DEF456', 'Weekend Dinner', 'Family dinner picker for Saturday night', 'assets/images/fish.png', 1, NOW(), NOW(), 2),

    ('http://localhost:4200/link/GHI789', 'Marketing Meetup', 'Marketing department lunch discussion session', 'assets/images/fish.png', 1, NOW(), NOW(), 1),

    ('http://localhost:4200/link/JKL012', 'Closed Session Example', 'This session is already closed and inactive', 'assets/images/fish.png', 0, NOW(), NOW(), 1),

    ('http://localhost:4200/link/MNO345', 'Friends Gathering', 'Deciding on restaurant for friends meetup', 'assets/images/fish.png', 1, NOW(), NOW(), 2);
 

INSERT INTO `restaurant-picker-02`.`session_restaurant`
    (`name`, `description`, `active`, `date_created`, `last_updated`, `session_id`)
VALUES
    -- For Session 1 (Team Lunch Friday)
    ('Pasta Palace', 'Known for fresh handmade pasta and cozy ambiance', 1, NOW(), NOW(), 1),
    ('Burger Haven', 'Juicy gourmet burgers with truffle fries', 1, NOW(), NOW(), 1),
    ('Sushi Express', 'Quick sushi rolls and sashimi platters', 1, NOW(), NOW(), 1),

    -- For Session 2 (Weekend Dinner)
    ('Steakhouse 88', 'Premium cuts of beef grilled to perfection', 1, NOW(), NOW(), 2),
    ('Thai Spice Kitchen', 'Authentic Thai food with spicy curries', 1, NOW(), NOW(), 2),

    -- For Session 3 (Marketing Meetup)
    ('Taco Fiesta', 'Mexican street tacos and nachos', 1, NOW(), NOW(), 3),
    ('Hotpot Paradise', 'All-you-can-eat hotpot with fresh seafood', 1, NOW(), NOW(), 3),

    -- For Session 4 (Closed Session Example)
    ('Vegan Garden', 'Healthy plant-based meals and smoothies', 0, NOW(), NOW(), 4),

    -- For Session 5 (Friends Gathering)
    ('Korean BBQ Street', 'Table-top BBQ with unlimited sides', 1, NOW(), NOW(), 5),
    ('Mediterraneo', 'Italian-Mediterranean fusion cuisine', 1, NOW(), NOW(), 5);



