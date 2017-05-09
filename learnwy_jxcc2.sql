-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: learnwy_jxcc
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `learnwy_jxcc`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `learnwy_jxcc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `learnwy_jxcc`;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish` (
  `dish_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(50) NOT NULL,
  `dish_price` decimal(6,2) NOT NULL,
  `dish_img_path` varchar(100) NOT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `dish_name` (`dish_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'手撕鸡',32.00,'/images/dish/shoushiji.png'),(2,'干锅土豆片',40.00,'/images/dish/ganguotudoupian.png');
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_food`
--

DROP TABLE IF EXISTS `dish_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish_food` (
  `dish_food_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  PRIMARY KEY (`dish_food_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_food`
--

LOCK TABLES `dish_food` WRITE;
/*!40000 ALTER TABLE `dish_food` DISABLE KEYS */;
INSERT INTO `dish_food` VALUES (1,1,1),(2,2,2);
/*!40000 ALTER TABLE `dish_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `food` (
  `food_id` int(11) NOT NULL AUTO_INCREMENT,
  `food_name` varchar(50) NOT NULL,
  `food_price` decimal(6,2) NOT NULL,
  `food_img_path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`food_id`),
  UNIQUE KEY `food_name` (`food_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'鸡1',10.00,'images/food/ji1.png'),(2,'土豆',8.00,'images/food/tudou.png');
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `table_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2017-05-09 07:37:06',1),(2,'2017-05-09 07:48:28',2);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_dish`
--

DROP TABLE IF EXISTS `order_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_dish` (
  `order_dish_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `dish_id` int(11) DEFAULT NULL,
  `nums` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`order_dish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_dish`
--

LOCK TABLES `order_dish` WRITE;
/*!40000 ALTER TABLE `order_dish` DISABLE KEYS */;
INSERT INTO `order_dish` VALUES (1,1,1,2),(2,2,2,4);
/*!40000 ALTER TABLE `order_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `power`
--

DROP TABLE IF EXISTS `power`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `power` (
  `display_name` varchar(50) NOT NULL,
  `power_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`power_id`),
  UNIQUE KEY `display_name_UNIQUE` (`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `power`
--

LOCK TABLES `power` WRITE;
/*!40000 ALTER TABLE `power` DISABLE KEYS */;
INSERT INTO `power` VALUES ('主厨',6),('老板',7),('超级管理员',1);
/*!40000 ALTER TABLE `power` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `display_name` varchar(50) NOT NULL,
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `display_name_UNIQUE` (`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('主厨',32),('超级管理员',1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_power`
--

DROP TABLE IF EXISTS `role_power`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_power` (
  `role_power_id` int(11) NOT NULL AUTO_INCREMENT,
  `power_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`role_power_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_power`
--

LOCK TABLES `role_power` WRITE;
/*!40000 ALTER TABLE `role_power` DISABLE KEYS */;
INSERT INTO `role_power` VALUES (1,1,1);
/*!40000 ALTER TABLE `role_power` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `sys_menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(50) NOT NULL,
  `path` varchar(50) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sys_menu_id`),
  UNIQUE KEY `display_name_UNIQUE` (`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'菜单管理','/sys_menu_manage',0),(2,'角色管理','#',0),(3,'权限管理','#',0),(4,'用户管理','#',0),(7,'角色权限管理','/role_power_manage',2),(8,'用户角色管理','/user_role_manage',4),(9,'用户修改','/user_manage',4),(10,'角色修改','/role_manage',2),(11,'权限修改','/power_manage',3),(14,'权限菜单管理','/power_sys_menu_manage',3),(15,'点菜','/dish_manage',0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_power`
--

DROP TABLE IF EXISTS `sys_menu_power`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu_power` (
  `sys_menu_power_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_menu_id` int(11) DEFAULT NULL,
  `power_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sys_menu_power_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_power`
--

LOCK TABLES `sys_menu_power` WRITE;
/*!40000 ALTER TABLE `sys_menu_power` DISABLE KEYS */;
INSERT INTO `sys_menu_power` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,7,1),(6,8,1),(7,9,1),(8,10,1),(9,11,1),(10,14,1);
/*!40000 ALTER TABLE `sys_menu_power` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `display_name` varchar(30) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_pwd` varchar(30) NOT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('超级管理员','name','pwd',1),('汪洋','learnwy','learnwy',2),('张涛','zt','zt',27);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-09 16:50:30
