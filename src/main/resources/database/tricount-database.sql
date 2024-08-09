DROP DATABASE IF EXISTS `tgpr-2324-a04`;
CREATE DATABASE IF NOT EXISTS `tgpr-2324-a04`;
USE `tgpr-2324-a04`;

CREATE TABLE `users`
(
    `id`              int(11)                                       NOT NULL AUTO_INCREMENT,
    `mail`            varchar(256) COLLATE utf8_unicode_ci          NOT NULL,
    `hashed_password` varchar(512) COLLATE utf8_unicode_ci          NOT NULL,
    `full_name`       varchar(256) COLLATE utf8_unicode_ci          NOT NULL,
    `role`            enum ('user','admin') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'user',
    `iban`            varchar(256) COLLATE utf8_unicode_ci                   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Mail` (`mail`),
    UNIQUE KEY `FullName` (`full_name`)
);

INSERT INTO `users`
VALUES (1, 'boverhaegen@epfc.eu', '56ce92d1de4f05017cf03d6cd514d6d1', 'Boris', 'user', NULL),
       (2, 'bepenelle@epfc.eu', '56ce92d1de4f05017cf03d6cd514d6d1', 'Benoît', 'user', NULL),
       (3, 'xapigeolet@epfc.eu', '56ce92d1de4f05017cf03d6cd514d6d1', 'Xavier', 'user', NULL),
       (4, 'mamichel@epfc.eu', '56ce92d1de4f05017cf03d6cd514d6d1', 'Marc', 'user', 'BE99123412341234'),
       (5, 'admin@epfc.eu', '56ce92d1de4f05017cf03d6cd514d6d1', 'Admin', 'admin', NULL);

CREATE TABLE `tricounts`
(
    `id`          int(11)                              NOT NULL AUTO_INCREMENT,
    `title`       varchar(256) COLLATE utf8_unicode_ci NOT NULL,
    `description` varchar(1024) COLLATE utf8_unicode_ci         DEFAULT NULL,
    `created_at`  datetime                             NOT NULL DEFAULT current_timestamp(),
    `creator`     int(11)                              NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Title` (`title`, `creator`),
    KEY `Creator` (`creator`),
    CONSTRAINT `tricounts_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `tricounts`
VALUES (1, 'Gers 2022', NULL, '2022-10-10 18:42:24', 1),
       (2, 'Resto badminton', NULL, '2022-10-10 19:25:10', 1),
       (4, 'Vacances', 'A la mer du nord', '2022-10-10 19:31:09', 1),
       (5, 'Tricount #01', NULL, '2022-07-18 11:00:00', 1),
       (6, 'Tricount #02', NULL, '2022-07-18 11:00:01', 1),
       (7, 'Tricount #03', NULL, '2022-07-18 11:00:02', 1),
       (8, 'Tricount #04', NULL, '2022-07-18 11:00:03', 1),
       (9, 'Tricount #05', NULL, '2022-07-18 11:00:04', 1),
       (10, 'Tricount #06', NULL, '2022-07-18 11:00:05', 1),
       (11, 'Tricount #07', NULL, '2022-07-18 11:00:06', 1),
       (12, 'Tricount #08', NULL, '2022-07-18 11:00:07', 1),
       (13, 'Tricount #09', NULL, '2022-07-18 11:00:08', 1),
       (14, 'Tricount #10', NULL, '2022-07-18 11:00:09', 1),
       (15, 'Tricount #11', NULL, '2022-07-18 11:00:10', 1),
       (16, 'Tricount #12', NULL, '2022-07-18 11:00:11', 1),
       (17, 'Tricount #13', NULL, '2022-07-18 11:00:12', 1),
       (18, 'Tricount #14', NULL, '2022-07-18 11:00:13', 1),
       (19, 'Tricount #15', NULL, '2022-07-18 11:00:14', 1),
       (20, 'Tricount #16', NULL, '2022-07-18 11:00:15', 1),
       (21, 'Tricount #17', NULL, '2022-07-18 11:00:16', 1),
       (22, 'Tricount #18', NULL, '2022-07-18 11:00:17', 1),
       (23, 'Tricount #19', NULL, '2022-07-18 11:00:18', 1),
       (24, 'Tricount #20', NULL, '2022-07-18 11:00:19', 1);

CREATE TABLE `subscriptions`
(
    `tricount` int(11) NOT NULL,
    `user`     int(11) NOT NULL,
    PRIMARY KEY (`tricount`, `user`),
    KEY `User` (`user`),
    CONSTRAINT `subscriptions_ibfk_1` FOREIGN KEY (`tricount`) REFERENCES `tricounts` (`id`) ON DELETE CASCADE,
    CONSTRAINT `subscriptions_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `subscriptions`
VALUES (2, 1),
       (4, 1),
       (1, 1),
       (2, 2),
       (4, 2),
       (4, 3),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1),
       (13, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (17, 1),
       (18, 1),
       (19, 1),
       (20, 1),
       (21, 1),
       (22, 1),
       (23, 1),
       (24, 1);

CREATE TABLE `operations`
(
    `id`             int(11)                              NOT NULL AUTO_INCREMENT,
    `title`          varchar(256) COLLATE utf8_unicode_ci NOT NULL,
    `tricount`       int(11)                              NOT NULL,
    `amount`         double                               NOT NULL,
    `operation_date` date                                 NOT NULL,
    `initiator`      int(11)                              NOT NULL,
    `created_at`     datetime                             NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `Initiator` (`initiator`),
    KEY `Tricount` (`tricount`),
    CONSTRAINT `operations_ibfk_1` FOREIGN KEY (`initiator`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `operations_ibfk_2` FOREIGN KEY (`tricount`) REFERENCES `tricounts` (`id`) ON DELETE CASCADE
);

INSERT INTO `operations`
VALUES (1, 'Colruyt', 4, 100, '2022-10-13', 2, '2022-10-13 19:09:18'),
       (2, 'Plein essence', 4, 75, '2022-10-13', 1, '2022-10-13 20:10:41'),
       (3, 'Grosses courses LIDL', 4, 212.47, '2022-10-13', 3, '2022-10-13 21:23:49'),
       (4, 'Apéros', 4, 31.897456217, '2022-10-13', 1, '2022-10-13 23:51:20'),
       (5, 'Boucherie', 4, 25.5, '2022-10-26', 2, '2022-10-26 09:59:56'),
       (6, 'Loterie', 4, 35, '2022-10-26', 1, '2022-10-26 10:02:24');

CREATE TABLE `repartitions`
(
    `operation` int(11) NOT NULL,
    `user`      int(11) NOT NULL,
    `weight`    int(11) NOT NULL,
    PRIMARY KEY (`operation`, `user`),
    KEY `User` (`user`),
    CONSTRAINT `repartitions_ibfk_1` FOREIGN KEY (`operation`) REFERENCES `operations` (`id`) ON DELETE CASCADE,
    CONSTRAINT `repartitions_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `repartitions`
VALUES (1, 1, 1),
       (1, 2, 1),
       (2, 1, 1),
       (2, 2, 1),
       (3, 1, 2),
       (3, 2, 1),
       (3, 3, 1),
       (4, 1, 1),
       (4, 2, 2),
       (4, 3, 3),
       (5, 1, 2),
       (5, 2, 1),
       (5, 3, 1),
       (6, 1, 1),
       (6, 3, 1);

CREATE TABLE `templates`
(
    `id`       int(11)                              NOT NULL AUTO_INCREMENT,
    `title`    varchar(256) COLLATE utf8_unicode_ci NOT NULL,
    `tricount` int(11)                              NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Title` (`title`, `tricount`),
    KEY `Tricount` (`tricount`),
    CONSTRAINT `templates_ibfk_1` FOREIGN KEY (`tricount`) REFERENCES `tricounts` (`id`) ON DELETE CASCADE
);

INSERT INTO `templates`
VALUES (2, 'Benoit ne paye rien', 4),
       (1, 'Boris paye double', 4);


CREATE TABLE `template_items`
(
    `user`     int(11) NOT NULL,
    `template` int(11) NOT NULL,
    `weight`   int(11) NOT NULL,
    PRIMARY KEY (`user`, `template`),
    KEY `Distribution` (`template`),
    CONSTRAINT `template_items_ibfk_1` FOREIGN KEY (`template`) REFERENCES `templates` (`id`) ON DELETE CASCADE,
    CONSTRAINT `template_items_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `template_items`
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 1, 1),
       (3, 1, 1),
       (3, 2, 1);
