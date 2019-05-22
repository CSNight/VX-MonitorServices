-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: wechat
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `chat_rooms`
--

DROP TABLE IF EXISTS `chat_rooms`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `chat_rooms`
(
    `id`             varchar(50) NOT NULL,
    `user_id`        varchar(50)   DEFAULT NULL,
    `room_id`        varchar(50)   DEFAULT NULL,
    `room_code`      varchar(100)  DEFAULT NULL,
    `room_nick`      varchar(100)  DEFAULT NULL,
    `room_owner`     varchar(50)   DEFAULT NULL,
    `member_count`   int(11)       DEFAULT NULL,
    `is_owner`       tinyint(1)    DEFAULT NULL,
    `uin`            varchar(50)   DEFAULT NULL,
    `small_head`     mediumblob,
    `small_head_url` varchar(1000) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `chat_rooms_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collections`
--

DROP TABLE IF EXISTS `collections`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `collections`
(
    `id`      varchar(50) NOT NULL,
    `user_id` varchar(50)      DEFAULT NULL,
    `col_id`  varchar(100)     DEFAULT NULL,
    `seq`     varchar(100)     DEFAULT NULL,
    `times`   timestamp   NULL DEFAULT NULL,
    `type`    int(11)          DEFAULT NULL,
    `obj`     mediumblob,
    PRIMARY KEY (`id`),
    UNIQUE KEY `collections_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `contact`
(
    `id`             varchar(50) NOT NULL,
    `user_id`        varchar(50)   DEFAULT NULL COMMENT '所属微信用户id',
    `contact_id`     varchar(50)   DEFAULT NULL COMMENT '联系人微信号',
    `contact_name`   varchar(100)  DEFAULT NULL COMMENT '联系人昵称',
    `remark`         varchar(100)  DEFAULT NULL,
    `loc`            varchar(100)  DEFAULT NULL,
    `sex`            int(11)       DEFAULT NULL,
    `stranger`       varchar(200)  DEFAULT NULL,
    `signature`      varchar(500)  DEFAULT NULL,
    `add_source`     varchar(50)   DEFAULT NULL,
    `uin`            varchar(50)   DEFAULT NULL,
    `small_head`     mediumblob COMMENT '联系人头像',
    `small_head_url` varchar(1000) DEFAULT NULL,
    `big_head`       mediumblob,
    `big_head_url`   varchar(1000) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `contract_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `msg_file`
--

DROP TABLE IF EXISTS `msg_file`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `msg_file`
(
    `id`        varchar(50) NOT NULL,
    `user_id`   varchar(50)      DEFAULT NULL,
    `from_id`   varchar(50)      DEFAULT NULL,
    `meta`      varchar(1000)    DEFAULT NULL,
    `file_name` varchar(100)     DEFAULT NULL,
    `file_blob` longblob,
    `ext`       varchar(50)      DEFAULT NULL,
    `file_time` timestamp   NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `msg_file_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `msg_log`
--

DROP TABLE IF EXISTS `msg_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `msg_log`
(
    `id`           varchar(50) NOT NULL,
    `user_id`      varchar(50)      DEFAULT NULL,
    `msg_id`       varchar(50)      DEFAULT NULL,
    `msg_time`     timestamp   NULL DEFAULT NULL,
    `msg_type`     int(11)          DEFAULT NULL,
    `msg_subtype`  int(11)          DEFAULT NULL,
    `continues`    int(11)          DEFAULT NULL,
    `msg_status`   int(11)          DEFAULT NULL,
    `from_user`    varchar(50)      DEFAULT NULL,
    `from_type`    varchar(20)      DEFAULT NULL,
    `to_user`      varchar(50)      DEFAULT NULL,
    `to_type`      varchar(20)      DEFAULT NULL,
    `uin`          varchar(100)     DEFAULT NULL,
    `msg_source`   varchar(300)     DEFAULT NULL,
    `msg_describe` mediumblob,
    `descriptions` varchar(500)     DEFAULT NULL,
    `content_id`   varchar(50)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `msg_log_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `public_ct`
--

DROP TABLE IF EXISTS `public_ct`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `public_ct`
(
    `id`             varchar(50) NOT NULL,
    `user_id`        varchar(50)   DEFAULT NULL,
    `pct_id`         varchar(100)  DEFAULT NULL,
    `pct_name`       varchar(200)  DEFAULT NULL,
    `loc`            varchar(200)  DEFAULT NULL,
    `sign`           varchar(500)  DEFAULT NULL,
    `intro`          varchar(200)  DEFAULT NULL,
    `stranger`       varchar(500)  DEFAULT NULL,
    `add_source`     int(11)       DEFAULT NULL,
    `uin`            varchar(50)   DEFAULT NULL,
    `small_head`     mediumblob,
    `small_head_url` varchar(1000) DEFAULT NULL,
    `big_head`       mediumblob,
    `big_head_url`   varchar(1000) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `public_ct_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `room_members`
--

DROP TABLE IF EXISTS `room_members`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `room_members`
(
    `id`             varchar(50) NOT NULL,
    `user_id`        varchar(50)   DEFAULT NULL,
    `room_id`        varchar(50)   DEFAULT NULL,
    `member_id`      varchar(50)   DEFAULT NULL,
    `member_name`    varchar(100)  DEFAULT NULL,
    `member_nick`    varchar(100)  DEFAULT NULL,
    `invited_by`     varchar(50)   DEFAULT NULL,
    `small_head`     mediumblob,
    `small_head_url` varchar(1000) DEFAULT NULL,
    `big_head`       mediumblob,
    `big_head_url`   varchar(1000) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `room_members_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `strategy`
(
    `id`        varchar(50) NOT NULL,
    `type`      varchar(50)  DEFAULT NULL,
    `operation` varchar(50)  DEFAULT NULL,
    `target`    varchar(100) DEFAULT NULL,
    `source`    varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `strategy_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dic`
--

DROP TABLE IF EXISTS `sys_dic`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_dic`
(
    `id`        varchar(50) NOT NULL,
    `dic_type`  varchar(50)  DEFAULT NULL,
    `dic_name`  varchar(100) DEFAULT NULL,
    `dic_value` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_dic_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_user`
--

DROP TABLE IF EXISTS `wechat_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `wechat_user`
(
    `id`         varchar(50) NOT NULL,
    `user_id`    varchar(50)   DEFAULT NULL,
    `user_name`  varchar(100)  DEFAULT NULL,
    `pwd`        varchar(200)  DEFAULT NULL,
    `str62`      varchar(1000) DEFAULT NULL,
    `user_sign`  varchar(500)  DEFAULT NULL,
    `loc`        varchar(200)  DEFAULT NULL,
    `sex`        int(11)       DEFAULT NULL,
    `head_image` mediumblob,
    `qr_code`    mediumblob,
    PRIMARY KEY (`id`),
    UNIQUE KEY `wechat_user_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-05-17 15:54:51
