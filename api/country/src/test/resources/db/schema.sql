CREATE TABLE `country`
(
    `id`           LONG PRIMARY KEY AUTO_INCREMENT      NOT NULL,
    `name`         VARCHAR(255) UNIQUE                  NOT NULL,
    `isocode`      LONG UNIQUE                          NOT NULL,
    `shortcode`    varchar UNIQUE                       NOT NULL
);