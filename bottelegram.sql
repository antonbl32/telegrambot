-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: bot
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs4j12sfj254yawphx0k1xrl3f` (`question_id`),
  CONSTRAINT `FKs4j12sfj254yawphx0k1xrl3f` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'Online',2),(2,'Offline',2),(3,'2 раза в неделю',3),(4,'1 раз в неделю',3),(5,'1 раз в 2 недели',3),(6,'раз в месяц',3),(7,'будни',4),(8,'выходные',4),(9,'с 9 до 12',5),(10,'с 12 до 15',5),(11,'с 15 до 18',5),(12,'с 18 до 21',5),(13,'мальчик',6),(14,'девочка',6),(15,'до года',7),(16,'до 2 лет',7),(17,'2-3 года',7),(18,'до 5 лет',7),(19,'до 10 лет',7),(20,'старше 10 лет',7),(21,'дети',8),(22,'отношения',8),(23,'личностный рост',8),(24,'бытовые вопросы - лайфхаки',8),(25,'бизнес',8),(26,'обучение',8),(27,'работа',8),(28,'Начнем поиск?',1);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_answer_my_answers`
--

DROP TABLE IF EXISTS `my_answer_my_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_answer_my_answers` (
  `my_answer_id` int NOT NULL,
  `answer` int DEFAULT NULL,
  KEY `FKh4iu1ubm9n6g0nqebnw5nbjjy` (`my_answer_id`),
  CONSTRAINT `FKh4iu1ubm9n6g0nqebnw5nbjjy` FOREIGN KEY (`my_answer_id`) REFERENCES `myanswer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_answer_my_answers`
--

LOCK TABLES `my_answer_my_answers` WRITE;
/*!40000 ALTER TABLE `my_answer_my_answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_answer_my_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myanswer`
--

DROP TABLE IF EXISTS `myanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myanswer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_id` int DEFAULT NULL,
  `myanswer_id` int NOT NULL,
  `answer` int DEFAULT NULL,
  `user_bot_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7rfej4fu9yir5p2aejgb0ahwc` (`question_id`),
  KEY `FK3lx57iry0bp0wutwerklvp549` (`myanswer_id`),
  KEY `FKfxgvqsfd76d0cyjf26ptd3b3w` (`user_bot_id`),
  CONSTRAINT `FK3lx57iry0bp0wutwerklvp549` FOREIGN KEY (`myanswer_id`) REFERENCES `myanswer` (`id`),
  CONSTRAINT `FK7rfej4fu9yir5p2aejgb0ahwc` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FKfxgvqsfd76d0cyjf26ptd3b3w` FOREIGN KEY (`user_bot_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myanswer`
--

LOCK TABLES `myanswer` WRITE;
/*!40000 ALTER TABLE `myanswer` DISABLE KEYS */;
/*!40000 ALTER TABLE `myanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ask` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Привет, %s!\\nЧтобы подобрать тебе самую подходящую маму для встреч исходя из твоих интересов, ответь пожалуйста, на несколько вопросов:','NONE'),(2,'Какой формат встреч для тебя предпочтительный?','ONE'),(3,'Как часто ты бы хотел(а) общаться?','TWO'),(4,'Оптимальные дни для встреч для тебя?','THREE'),(5,'Оптимальные время дня для встреч для тебя?','FOUR'),(6,'Какую компанию хотели бы для деток?','FIVE'),(7,'Выберите возраст:','SIX'),(8,'Какие профессиональные и личные темы хотели бы обсуждать?','SEVEN'),(9,'all','EIGHT');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chatid` bigint DEFAULT NULL,
  `state` int DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1071087542,0,'Antonbl32','NONE'),(2,215311938,NULL,'aiataya','NONE');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-14 22:11:46
