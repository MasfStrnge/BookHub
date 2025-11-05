/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.0.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: bookHub
-- ------------------------------------------------------
-- Server version	12.0.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `lista`
--

DROP TABLE IF EXISTS `lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista` (
  `id_lista` int(11) NOT NULL AUTO_INCREMENT,
  `id_perfil` int(11) NOT NULL,
  `nome_lista` varchar(100) NOT NULL,
  `qt_livro` int(11) DEFAULT 0,
  `data_criacao` date DEFAULT NULL,
  PRIMARY KEY (`id_lista`),
  KEY `id_perfil` (`id_perfil`),
  CONSTRAINT `lista_ibfk_1` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `lista` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `lista_livro`
--

DROP TABLE IF EXISTS `lista_livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_livro` (
  `id_lista` int(11) NOT NULL,
  `id_livro` int(11) NOT NULL,
  PRIMARY KEY (`id_lista`,`id_livro`),
  KEY `id_livro` (`id_livro`),
  CONSTRAINT `lista_livro_ibfk_1` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`),
  CONSTRAINT `lista_livro_ibfk_2` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_livro`
--

LOCK TABLES `lista_livro` WRITE;
/*!40000 ALTER TABLE `lista_livro` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `lista_livro` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `livro`
--

DROP TABLE IF EXISTS `livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `livro` (
  `id_livro` int(11) NOT NULL AUTO_INCREMENT,
  `capa` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `ano_publicacao` int(11) NOT NULL,
  `isbn` varchar(80) NOT NULL,
  `genero` varchar(100) NOT NULL,
  `qt_pagina` int(11) NOT NULL,
  `idioma` varchar(20) NOT NULL,
  PRIMARY KEY (`id_livro`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `livro` VALUES
(44,'capas/memoriasPostumasBrasCubas.jpg','Memórias Póstumas de Brás Cubas','Machado de Assis',1997,'85-250-1762-0','Clássico',208,'Português'),
(45,'capas/DomCasmurro.jpg','Dom Casmurro','Machado de Assis',1899,'85-250-1761-2','Clássico',218,'Português'),
(46,'capas/MaoLuva.jpg','A Mão e a Luva','Machado de Assis',1874,'85-250-1763-9','Clássico',108,'Português'),
(47,'capas/EdgarAllanPoeMedoClassico.jpg','Edgar Allan Poe: Medo Clássico','Edgar Allan Poe',2016,'978-85-945-4024-9','Horror',384,'Português'),
(48,'capas/LabirintoFauno.jpg','O Labirinto do Fauno','Guillermo del Toro, Cornelia Funker',2019,'978-85-510-0519-4','Fantasia',320,'Português'),
(49,'capas/ItCoisa.jpg','It: A coisa','Stephen King',2018,'9788560280940','Horror',1104,'Português'),
(50,'capas/Sapiens.jpg','Sapiens: uma breve história da humanidade','Yuval Noah Harari',2020,'9788535933925','História',472,'Português'),
(51,'capas/MarcaDeAtena.jpg','A Marca de Atena','Rick Riordan',2013,'9788580573107','Fantasia',480,'Português'),
(52,'capas/FilhoDeNetuno.jpg','O Filho de Netuno','Rick Riordan',2012,'9788580571806','Fantasia',432,'Português'),
(53,'capas/HeroiPerdido.jpg','O Herói Perdido','Rick Riordan',2011,'9788580570083','Fantasia',440,'Português'),
(54,'capas/Frankstein.jpg','Frankenstein, ou o Prometeu Moderno','Mary Wollstonecraft Shelley',2017,'9788594540188','Clássico',304,'Português'),
(55,'capas/MessiasDuna.jpg','Messias de Duna','Frank Herbert',2017,'9788576573821','Ficção Científica',272,'Português'),
(56,'capas/FilhosDuna.jpg','Filhos de Duna','Frank Herbert',2017,'9788576573142','Ficção Científica',528,'Português'),
(57,'capas/Duna.jpg','Duna','Frank Herbert',2017,'9788576573135','Ficção Científica',677,'Português');
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id_perfil` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `fotoPerfil` varchar(255) DEFAULT NULL,
  `imagem_fundo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `sobrenome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nome_usuario` (`nome_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `usuario` VALUES
(8,'MasfStrange','123','manu.code24@gmail.com','Manuela','Ferreira');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping routines for database 'bookHub'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-11-05 10:13:27
