-- New SQL Script
CREATE TABLE IF NOT EXISTS `Golazo`.`User` (
                                               `username` VARCHAR(255) NOT NULL,
                                               `name` VARCHAR(255) NOT NULL,
                                               `email` VARCHAR(255) NOT NULL,
                                               `password` VARCHAR(255) NOT NULL,
                                               `dateOfBirth` DATETIME(6) NULL DEFAULT NULL,
                                               `createdOn` DATETIME NOT NULL,
                                               `lastUpdated` DATETIME(6) NULL DEFAULT NULL,
                                               PRIMARY KEY (`username`),
                                               UNIQUE INDEX `email` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `Golazo`.`Goal` (
                                               `id` VARCHAR(255) NOT NULL,
                                               `username` VARCHAR(255) NOT NULL,
                                               `title` VARCHAR(255) NOT NULL,
                                               `subtitle` VARCHAR(255) NULL DEFAULT NULL,
                                               `notes` VARCHAR(255) NULL DEFAULT NULL,
                                               `statusId` VARCHAR(255) NULL DEFAULT NULL,
                                               `startDate` DATETIME NOT NULL,
                                               `completionDate` DATETIME NULL DEFAULT NULL,
                                               `lastUpdated` DATETIME NOT NULL,
                                               `isChildGoal` CHAR(1) NULL DEFAULT NULL,
                                               `parentGoalId` VARCHAR(255) NULL DEFAULT NULL,
                                               `createdOn` DATETIME NOT NULL,
                                               `lastUpdate` DATETIME(6) NULL DEFAULT NULL,
                                               PRIMARY KEY (`id`),
                                               INDEX `username` (`username` ASC) VISIBLE,
                                               INDEX `parentGoalId` (`parentGoalId` ASC) VISIBLE,
                                               CONSTRAINT `goal_ibfk_1`
                                                   FOREIGN KEY (`username`)
                                                       REFERENCES `Golazo`.`User` (`username`),
                                               CONSTRAINT `goal_ibfk_2`
                                                   FOREIGN KEY (`parentGoalId`)
                                                       REFERENCES `Golazo`.`Goal` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `Golazo`.`GoalStatus` (
                                                     `id` VARCHAR(255) NOT NULL,
                                                     `username` VARCHAR(255) NOT NULL,
                                                     `title` VARCHAR(255) NULL DEFAULT NULL,
                                                     PRIMARY KEY (`id`),
                                                     INDEX `username` (`username` ASC) VISIBLE,
                                                     CONSTRAINT `goalstatus_ibfk_1`
                                                         FOREIGN KEY (`username`)
                                                             REFERENCES `Golazo`.`User` (`username`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `Golazo`.`Content` (
                                                  `id` VARCHAR(255) NOT NULL,
                                                  `username` VARCHAR(255) NOT NULL,
                                                  `title` VARCHAR(255) NOT NULL,
                                                  `message` VARCHAR(255) NULL DEFAULT NULL,
                                                  `createdOn` DATETIME NOT NULL,
                                                  `likeCount` INT NULL DEFAULT NULL,
                                                  `sharedPostId` VARCHAR(255) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`),
                                                  INDEX `username` (`username` ASC) VISIBLE,
                                                  CONSTRAINT `content_ibfk_1`
                                                      FOREIGN KEY (`username`)
                                                          REFERENCES `Golazo`.`User` (`username`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `Golazo`.`LikedBy` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `username` VARCHAR(255) NOT NULL,
                                                  `contentId` VARCHAR(255) NOT NULL,
                                                  `likedOn` DATETIME NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`),
                                                  INDEX `username` (`username` ASC) VISIBLE,
                                                  INDEX `contentId` (`contentId` ASC) VISIBLE,
                                                  CONSTRAINT `likedby_ibfk_1`
                                                      FOREIGN KEY (`username`)
                                                          REFERENCES `Golazo`.`User` (`username`),
                                                  CONSTRAINT `likedby_ibfk_2`
                                                      FOREIGN KEY (`contentId`)
                                                          REFERENCES `Golazo`.`Content` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE FollowPair (
                            userA VARCHAR(255) NOT NULL,
                            userB VARCHAR(255) NOT NULL,
                            followedOn DATETIME NOT NULL,
                            PRIMARY KEY (userA, userB),
                            FOREIGN KEY (userA)
                                REFERENCES User (username),
                            FOREIGN KEY (userB)
                                REFERENCES User (username)
);