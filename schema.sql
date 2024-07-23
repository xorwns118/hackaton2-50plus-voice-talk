DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS interests;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS user_interests;

create table users
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

create table interests
(
    interest_id   BIGINT(32)  NOT NULL AUTO_INCREMENT,
    interest_name VARCHAR(10) NOT NULL,
    PRIMARY KEY (interest_id)
);

create table notifications
(
    notification_id BIGINT(32)   NOT NULL AUTO_INCREMENT,
    noti_message    VARCHAR(100) NULL,
    noti_created_at DATETIME     NOT NULL,
    PRIMARY KEY (notification_id)
);

CREATE TABLE user_interests
(
    user_id     BIGINT(32) NOT NULL,
    interest_id BIGINT(32) NOT NULL,
    PRIMARY KEY (user_id, interest_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (interest_id) REFERENCES interests (interest_id)
);