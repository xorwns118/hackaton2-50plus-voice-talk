DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS Interests;
DROP TABLE IF EXISTS Notifications;
DROP TABLE IF EXISTS User_Interests;

create table Users
(
    user_id       BIGINT(32)  NOT NULL AUTO_INCREMENT,
    username     VARCHAR(50) NOT NULL,
    password      VARCHAR(8)  NOT NULL,
    gender        INT         NOT NULL,
    birth_date    VARCHAR(50) NOT NULL,
    authority     INT         NOT NULL,
    mobile_number VARCHAR(20) NOT NULL,
    province      VARCHAR(50),
    city          VARCHAR(50),
    PRIMARY KEY (user_id)
);

create table Interests
(
    interest_id   BIGINT(32)  NOT NULL AUTO_INCREMENT,
    interest_name VARCHAR(10) NOT NULL,
    PRIMARY KEY (interest_id)
);

create table Notifications
(
    notification_id BIGINT(32)   NOT NULL AUTO_INCREMENT,
    noti_message    VARCHAR(100) NULL,
    noti_created_at DATETIME     NOT NULL,
    PRIMARY KEY (notification_id)
);

CREATE TABLE User_Interests
(
    user_id     BIGINT(32) NOT NULL,
    interest_id BIGINT(32) NOT NULL,
    PRIMARY KEY (user_id, interest_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (interest_id) REFERENCES Interests (interest_id)
);