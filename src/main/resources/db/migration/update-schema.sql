DROP SCHEMA Golazo;
CREATE SCHEMA Golazo;

use Golazo;

CREATE TABLE User (
                      id VARCHAR(255) NOT NULL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      dateOfBirth DATE NOT NULL,
                      lastUpdated DATETIME,
                      createdOn DATETIME NOT NULL
);

CREATE TABLE Post (
                      id VARCHAR(255) NOT NULL PRIMARY KEY,
                      userId VARCHAR(255) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      message VARCHAR(1000),
                      createdOn DATETIME NOT NULL,
                      FOREIGN KEY (userId)
                          REFERENCES User (id)
);

CREATE TABLE GoalStatus (
                            id VARCHAR(255) NOT NULL PRIMARY KEY,
                            userId VARCHAR(255) NOT NULL,
                            title VARCHAR(127) NOT NULL,
                            FOREIGN KEY (userId)
                                REFERENCES User (id)
);

CREATE TABLE Goal (
                      id VARCHAR(255) NOT NULL PRIMARY KEY,
                      userId VARCHAR(255) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      subtitle VARCHAR(255),
                      notes VARCHAR(1000),
                      statusId VARCHAR(255),
                      startDate DATETIME NOT NULL,
                      completionDate DATETIME,
                      lastUpdated DATETIME NOT NULL,
                      isChildGoal VARCHAR(1),
                      parentGoalId VARCHAR(255),
                      createdOn DATETIME NOT NULL,
                      FOREIGN KEY (userId)
                          REFERENCES User (id),
                      FOREIGN KEY (parentGoalId)
                          REFERENCES Goal (id)
);

CREATE TABLE Following (
                           id VARCHAR(255) NOT NULL PRIMARY KEY,
                           userA VARCHAR(255) NOT NULL,
                           userB VARCHAR(255) NOT NULL,
                           followedOn DATETIME NOT NULL,
                           FOREIGN KEY (userA)
                               REFERENCES User (id),
                           FOREIGN KEY (userB)
                               REFERENCES User (id)
);

CREATE TABLE `SPRING_SESSION` (
                                  `PRIMARY_ID` CHAR(36) NOT NULL,
                                  `SESSION_ID` CHAR(36) NOT NULL,
                                  `CREATION_TIME` BIGINT NOT NULL,
                                  `LAST_ACCESS_TIME` BIGINT NOT NULL,
                                  `MAX_INACTIVE_INTERVAL` INT NOT NULL,
                                  `EXPIRY_TIME` BIGINT NOT NULL,
                                  `PRINCIPAL_NAME` VARCHAR(100) NULL DEFAULT NULL,
                                  PRIMARY KEY (`PRIMARY_ID`),
                                  UNIQUE INDEX `SPRING_SESSION_IX1` (`SESSION_ID` ASC) VISIBLE,
                                  INDEX `SPRING_SESSION_IX2` (`EXPIRY_TIME` ASC) VISIBLE,
                                  INDEX `SPRING_SESSION_IX3` (`PRINCIPAL_NAME` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci
    ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `Golazo`.`SPRING_SESSION_ATTRIBUTES` (
                                                                    `SESSION_PRIMARY_ID` CHAR(36) NOT NULL,
                                                                    `ATTRIBUTE_NAME` VARCHAR(200) NOT NULL,
                                                                    `ATTRIBUTE_BYTES` BLOB NOT NULL,
                                                                    PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`),
                                                                    CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK`
                                                                        FOREIGN KEY (`SESSION_PRIMARY_ID`)
                                                                            REFERENCES `Golazo`.`SPRING_SESSION` (`PRIMARY_ID`)
                                                                            ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci
    ROW_FORMAT = DYNAMIC;