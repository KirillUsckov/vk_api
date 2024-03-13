-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: vk_db
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `request` varchar(100) DEFAULT NULL,
  `who` varchar(200) NOT NULL,
  `has_access` tinyint(1) NOT NULL,
  `body` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (4,'2024-03-13 12:29:15','/posts','editor',1,'Create /posts with params Post(userId=1, id=0, title=new title, body=new body)'),(5,'2024-03-13 12:29:19','/posts','admin',1,'Delete /posts with id 1'),(6,'2024-03-13 12:31:36','/albums','albums',1,'Delete /albums with id 10'),(7,'2024-03-13 13:07:06','/albums/10','posts',0,'Exception Access Denied'),(8,'2024-03-13 13:07:08','/albums/10','posts',0,'Exception Access Denied'),(9,'2024-03-13 13:07:53','/albums/10','posts',0,'Exception Access Denied'),(10,'2024-03-13 13:08:33','/albums/10','posts',0,'Exception Access Denied'),(11,'2024-03-13 13:09:17','/albums','admin',1,'Delete /albums with id 10'),(12,'2024-03-13 13:18:03','/posts','admin',1,'Update /posts with id 3'),(13,'2024-03-13 13:31:02','/posts','posts',1,'Get /posts with id : 1'),(14,'2024-03-13 13:31:08','/albums','admin',1,'Get /albums with id : 20'),(15,'2024-03-13 13:31:14','/users','admin',1,'Get /users with id : 1'),(16,'2024-03-13 13:33:14','/users','admin',1,'Get /users with id : 1'),(17,'2024-03-13 13:33:50','/posts','admin',1,'Get /posts with id : 3'),(18,'2024-03-13 13:33:50','/posts','admin',1,'Update /posts/3 with id 3'),(19,'2024-03-13 13:44:47','/posts','posts',1,'Get /posts with id : 3'),(20,'2024-03-13 13:44:47','/posts','posts',1,'Update /posts with id 3'),(21,'2024-03-13 13:44:55','/posts','posts',1,'Get /posts with id : 12'),(22,'2024-03-13 13:45:05','/posts','editor',1,'Create /posts with params Post(userId=1, id=0, title=new title, body=new body)'),(23,'2024-03-13 13:45:09','/posts','admin',1,'Get /posts with id : 1'),(24,'2024-03-13 13:45:09','/posts','admin',1,'Delete /posts with id 1'),(25,'2024-03-13 13:45:15','/albums','admin',1,'Get /albums with id : 20'),(26,'2024-03-13 13:45:29','/albums','admin',1,'Get /albums with id : 9'),(27,'2024-03-13 13:45:29','/albums','admin',1,'Delete /albums with id 9'),(28,'2024-03-13 13:45:35','/albums','admin',1,'Get /albums with id : -1'),(29,'2024-03-13 13:45:43','/albums','albums',1,'Create /albums with params Album(userId=1, id=0, title=New title)'),(30,'2024-03-13 13:45:54','/users','admin',1,'Get /users with id : 1'),(31,'2024-03-13 13:45:57','/users','admin',1,'Get /users with id : 3'),(32,'2024-03-13 13:46:03','/users','admin',1,'Get /users with id : 5'),(33,'2024-03-13 13:46:03','/users','admin',1,'Delete /users with id 5'),(34,'2024-03-13 13:47:16','/users/7/posts','posts',0,'Exception Access Denied'),(35,'2024-03-13 13:52:27','/posts','posts',1,'Get /posts with id : 1'),(36,'2024-03-13 13:55:14','/albums','albums',1,'Get /albums with id : 12'),(37,'2024-03-13 13:55:17','/albums/12/photos','albums',1,'Get posts for user with id 12'),(38,'2024-03-13 13:55:28','/albums/12/photos','albums',1,'Get posts for user with id 12'),(39,'2024-03-13 13:56:00','/albums/12/photos','albums',1,'Get posts for user with id 12'),(40,'2024-03-13 13:56:03','/albums/12/photos','albums',1,'Get posts for user with id 12'),(41,'2024-03-13 13:57:09','/albums','albums',1,'Get /albums with id : 12'),(42,'2024-03-13 13:57:09','/albums/12/photos','albums',1,'Get posts for user with id 12'),(43,'2024-03-13 13:57:11','/albums/12/photos','albums',1,'Get posts for user with id 12');
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USERS'),(3,'ROLE_POSTS'),(4,'ROLE_ALBUMS'),(5,'ROLE_POSTS_ALBUMS'),(6,'ROLE_VIEWER'),(7,'ROLE_ALBUMS_VIEWER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2a$12$uR76ktYUSZ3nMEBEo65XTeVJqeq7VybEJ4lJA8AmXMlht3dY/wOMS',1),(2,'albums','$2a$12$ordetpNymetl48EDz/3sfeKRbf.qiS7rp5HSUVUR5ImxSHG7CGoIy',4),(3,'posts','$2a$12$20CU3ibcURIbQAz10t4UjOO5JckBk7ZMzPpL9YtKpYLyGDGReJOmO',3),(4,'users','$2a$12$RgaW5q1HmQ2KJTCh6njHIOkjgo4oGQXHyPsB6P9lWVjFMqvkkSO.6',2),(5,'editor','$2a$12$dY4ZaubkKSr1wt.7FoWaveF.CQhI9APRQMA3bd1f5JGpZHPgb9UoC',5),(6,'viewer','$2a$12$ZgwM.ecQzoSV/uBolo63gOgEVEEws3s7NHlrNLBGEj6Mj708Cm3gS',6),(7,'alb_viewer','$2a$12$vUIgsb5/5LdQkavPU7nQ/e9kYFxNNCtgBcSZr04s0BKb6y20pq0Q.',7),(15,'kirill','$2a$10$punVXts.IAoXXcYaYHwCqu3FZYoEG9uNvnA26dZJNVZIGH4NtAP6.',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-13 14:23:07
