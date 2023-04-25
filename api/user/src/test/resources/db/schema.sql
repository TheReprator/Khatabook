CREATE TABLE `user`
(
    `id`             LONG PRIMARY KEY AUTO_INCREMENT      NOT NULL,
    `name`           VARCHAR(255) UNIQUE                  NOT NULL CHECK (LENGTH(`name`) > 3),
    `number`         LONG UNIQUE                          NOT NULL CHECK(`number` > 7),
    `countryCode`    varchar UNIQUE                       NOT NULL CHECK (LENGTH(`shortcode`) > 0)
);