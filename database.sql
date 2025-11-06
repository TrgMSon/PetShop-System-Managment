-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: database
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `idCustomer` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idCustomer`),
  UNIQUE KEY `idCustomer_UNIQUE` (`idCustomer`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('C01','Nguyễn Văn Anh','1234567'),('C02','Trần Thị Bình','1237654'),('C03','Lê Văn Cường','3112233222'),('C04','Phạm Thị Dung','333334'),('C06','Hoàng Thị Lan','222655'),('C07','Đỗ Văn Minh','565233'),('C08','Bùi Thị Hương','55545'),('C09','Phan Văn Nam','771199'),('C10','Nguyễn Thị Oanh','123811'),('C1067','An','12423'),('C11','Trần Văn Phúc','222733'),('C12','Lê Thị Quyên','11156'),('C13','Phạm Văn Sơn','00032'),('C15','Hoàng Văn Tuấn','00046'),('C16','Đỗ Thị Uyên','010133'),('C17','Bùi Văn Việt','110677'),('C18','Phan Thị Xuân','0027111111'),('C19','Nguyễn Thị Yến','002988'),('C20','Trần Thị Linh','001399'),('C3226','Lê Văn Cường','1234567890'),('C3538','An','1234234'),('C3769','Nguyễn Nam Anh','12423423'),('C3926','Hải','1111111111'),('C4380','An','1234567891'),('C496','Nguyễn Văn A','1234214'),('C5402','Nguyễn Anh','43523455'),('C6','Nguyễn Văn A','124342'),('C6060','An','135432'),('C6360','Nguyễn Văn A','12314532'),('C6886','An','1421342'),('C6939','Nguyễn Văn Anh','12472354'),('C700','Lê Văn Bình','1423423'),('C7623','Trương Minh Sơn','9876543210'),('C8065','Phan Thị Xuân','002711111'),('C8331','An','134234'),('C8663','Phạm Hải','14252345'),('C8936','Hải','8888888888'),('C9009','Nguyễn Hoàng Hà','972375'),('C9590','An','123414');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `idInvoice` varchar(45) NOT NULL,
  `totalAmount` decimal(10,0) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `idCustomer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idInvoice`),
  UNIQUE KEY `idInvoice_UNIQUE` (`idInvoice`),
  KEY `idCustomer_idx` (`idCustomer`),
  CONSTRAINT `idCustomer` FOREIGN KEY (`idCustomer`) REFERENCES `customer` (`idCustomer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES ('I006',2120000,'2025-09-03','C06'),('I1458',7320000,'2025-10-23','C3926'),('I1586',13300000,'2025-10-24','C8065'),('I1621',250000,'2025-10-24','C03'),('I1938',600000,'2025-10-04','C9590'),('I2140',17500000,'2025-10-05','C8663'),('I2987',9000000,'2025-10-05','C8663'),('I3190',6500000,'2025-09-24','C01'),('I3348',400000,'2025-10-04','C9590'),('I3502',400000,'2025-10-04','C9590'),('I4065',2500000,'2025-10-05','C700'),('I4125',3500000,'2025-10-05','C8663'),('I5148',400000,'2025-10-04','C9590'),('I5322',1000000,'2025-10-04','C9590'),('I5881',12000000,'2025-10-16','C8936'),('I60',600000,'2025-10-04','C9590'),('I7198',58500000,'2025-10-05','C8663'),('I7323',4000000,'2025-10-04','C9590'),('I7516',12500000,'2025-10-24','C03'),('I7781',3500000,'2025-09-26','C01'),('I78',6410000,'2025-10-25','C7623'),('I9106',3500000,'2025-10-05','C8663'),('I9133',1690000,'2025-10-23','C3926'),('I986',8000000,'2025-10-09','C01'),('I9982',3500000,'2025-10-05','C8663');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicedetail`
--

DROP TABLE IF EXISTS `invoicedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoicedetail` (
  `quantity` int NOT NULL,
  `nameProduct` varchar(45) DEFAULT NULL,
  `costProduct` decimal(10,0) DEFAULT NULL,
  `idProduct` varchar(45) NOT NULL,
  `idInvoice` varchar(45) NOT NULL,
  PRIMARY KEY (`idInvoice`,`idProduct`),
  KEY `idProdcut_idx` (`idProduct`),
  CONSTRAINT `idInvoice` FOREIGN KEY (`idInvoice`) REFERENCES `invoice` (`idInvoice`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idProduct` FOREIGN KEY (`idProduct`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoicedetail`
--

LOCK TABLES `invoicedetail` WRITE;
/*!40000 ALTER TABLE `invoicedetail` DISABLE KEYS */;
INSERT INTO `invoicedetail` VALUES (1,'Chuồng chó inox',1200000,'DD02','I006'),(1,'Áo quần cho chó nhỏ',120000,'DD05','I006'),(1,'Thỏ cảnh Mini Rex',800000,'TC06','I006'),(1,'Áo quần cho chó nhỏ',120000,'DD05','I1458'),(1,'Vòng cổ chống ve',200000,'DD06','I1458'),(1,'Mèo Ba Tư',7000000,'TC04','I1458'),(10,'Khay vệ sinh cho mèo',180000,'DD03','I1586'),(20,'Thức ăn hạt cho mèo (2kg)',250000,'DD08','I1586'),(1,'Mèo Anh lông ngắn',6500000,'TC03','I1586'),(1,'Lồng nuôi Hamster',250000,'DD01','I1621'),(3,'Mèo ta',200000,'TC6111','I1938'),(5,'Rùa cảnh Sulcata',3500000,'TC10','I2140'),(5,'Vẹt Cockatiel',1800000,'TC09','I2987'),(1,'Mèo Anh lông dài',6500000,'TC7307','I3190'),(2,'Mèo ta',200000,'TC6111','I3348'),(2,'Mèo ta',200000,'TC6111','I3502'),(10,'Thức ăn hạt cho mèo (2kg)',250000,'DD08','I4065'),(1,'Rùa cảnh Sulcata',3500000,'TC10','I4125'),(2,'Mèo ta',200000,'TC6111','I5148'),(5,'Mèo ta',200000,'TC6111','I5322'),(10,'Chuồng chó inox',1200000,'DD8922','I5881'),(3,'Mèo ta',200000,'TC6111','I60'),(13,'Rùa cảnh Sulcata',3500000,'TC10','I7198'),(2,'Mèo Anh lông dài',6500000,'TC7307','I7198'),(20,'Mèo ta',200000,'TC6111','I7323'),(50,'Thức ăn hạt cho mèo (2kg)',250000,'DD08','I7516'),(1,'Rùa cảnh Sulcata',3500000,'TC10','I7781'),(3,'Áo quần cho chó nhỏ',120000,'DD05','I78'),(11,'Thức ăn hạt cho chó (5kg)',550000,'DD09','I78'),(1,'Rùa cảnh Sulcata',3500000,'TC10','I9106'),(1,'Thức ăn hạt cho chó (5kg)',550000,'DD09','I9133'),(1,'Bể cá thủy sinh mini',900000,'DD10','I9133'),(2,'Cá Betta (cá xiêm)',120000,'TC07','I9133'),(10,'Thức ăn hạt cho mèo (2kg)',250000,'DD08','I986'),(10,'Thức ăn hạt cho chó (5kg)',550000,'DD09','I986'),(1,'Rùa cảnh Sulcata',3500000,'TC10','I9982');
/*!40000 ALTER TABLE `invoicedetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `idProduct` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `cost` decimal(10,0) DEFAULT NULL,
  `origin` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idProduct`),
  UNIQUE KEY `idProduct_UNIQUE` (`idProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('DD01','Lồng nuôi Hamster',250000,'Trung Quốc','Đồ dùng cho thú cưng'),('DD02','Chuồng chó inox',1200000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0206','Xà phòng tắm',90000,'Việt Nam','Đồ dùng cho thú cưng'),('DD03','Khay vệ sinh cho mèo',180000,'Việt Nam','Đồ dùng cho thú cưng'),('DD04','Đệm ngủ cho chó mèo',300000,'Việt Nam','Đồ dùng cho thú cưng'),('DD05','Áo quần cho chó nhỏ',120000,'Việt Nam','Đồ dùng cho thú cưng'),('DD06','Vòng cổ chống ve',200000,'Đức','Đồ dùng cho thú cưng'),('DD07','Dây dắt chó ',150000,'Việt Nam','Đồ dùng cho thú cưng'),('DD08','Thức ăn hạt cho mèo (2kg)',250000,'Việt Nam','Đồ dùng cho thú cưng'),('DD09','Thức ăn hạt cho chó (5kg)',550000,'Việt Nam','Đồ dùng cho thú cưng'),('DD10','Bể cá thủy sinh mini',900000,'Việt Nam','Đồ dùng cho thú cưng'),('DD8922','Chuồng chó inox',1200000,'Trung Quốc','Đồ dùng cho thú cưng'),('DD9954','Cát than hoạt tính',110000,'Việt Nam','Đồ dùng cho thú cưng'),('TC01','Chó Poodle mini',5000000,'Việt Nam','Thú cưng'),('TC02','Chó Corgi',12000000,'Thái Lan','Thú cưng'),('TC03','Mèo Anh lông ngắn',6500000,'Anh','Thú cưng'),('TC04','Mèo Ba Tư',7000000,'Iran','Thú cưng'),('TC05','Chuột Hamster Bear',150000,'Việt Nam','Thú cưng'),('TC06','Thỏ cảnh Mini Rex',800000,'Hà Lan','Thú cưng'),('TC07','Cá Betta (cá xiêm)',120000,'Thái Lan','Thú cưng'),('TC08','Chó Shiba',30000000,'Nhật Bản','Thú cưng'),('TC09','Vẹt Cockatiel',1800000,'Úc','Thú cưng'),('TC10','Rùa cảnh Sulcata',3500000,'Châu Phi','Thú cưng'),('TC6111','Mèo ta',200000,'Việt Nam','Thú cưng'),('TC6971','Chó Samoyed',20000000,'Phần Lan','Thú cưng'),('TC7307','Mèo Anh lông dài',6500000,'Anh','Thú cưng');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `idWarehouse` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `maxCapacity` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idWarehouse`),
  UNIQUE KEY `idWarehouse_UNIQUE` (`idWarehouse`),
  KEY `idProductW_idx` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES ('W001','Hà Nội','2000'),('W009','Bắc Ninh','1500'),('W010','Hải Phòng','2000'),('W2735','Đà Nẵng','1000'),('W3870','Bắc Ninh','2000'),('W6884','Hà Nội','2000'),('W7724','Đà Nẵng','1000'),('W997','Hà Nội','1000');
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehousedetail`
--

DROP TABLE IF EXISTS `warehousedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehousedetail` (
  `idWarehouse` varchar(45) NOT NULL,
  `idProductW` varchar(45) NOT NULL,
  `nameProduct` varchar(45) DEFAULT NULL,
  `lastReceiveDate` varchar(45) DEFAULT NULL,
  `quantityInStock` int DEFAULT NULL,
  PRIMARY KEY (`idWarehouse`,`idProductW`),
  KEY `idProductW_idx` (`idProductW`),
  CONSTRAINT `idProductW` FOREIGN KEY (`idProductW`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWarehouse` FOREIGN KEY (`idWarehouse`) REFERENCES `warehouse` (`idWarehouse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehousedetail`
--

LOCK TABLES `warehousedetail` WRITE;
/*!40000 ALTER TABLE `warehousedetail` DISABLE KEYS */;
INSERT INTO `warehousedetail` VALUES ('W001','DD01','Lồng nuôi Hamster','2025-10-25',10),('W001','DD02','Chuồng chó inox','2025-10-25',2),('W001','DD0206','Xà phòng tắm','2025-10-25',2),('W001','DD03','Khay vệ sinh cho mèo','2025-10-25',2),('W001','DD04','Đệm ngủ cho chó mèo','2025-10-25',2),('W001','DD05','Áo quần cho chó nhỏ','2025-10-25',2),('W001','DD06','Vòng cổ chống ve','2025-10-25',2),('W001','DD07','Dây dắt chó ','2025-10-25',2),('W001','DD08','Thức ăn hạt cho mèo (2kg)','2025-10-25',4),('W001','DD09','Thức ăn hạt cho chó (5kg)','2025-10-25',52),('W001','DD10','Bể cá thủy sinh mini','2025-10-25',2),('W001','DD8922','Chuồng chó inox','2025-10-25',2),('W001','DD9954','Cát than hoạt tính','2025-10-25',2),('W001','TC01','Chó Poodle mini','2025-10-25',12),('W001','TC02','Chó Corgi','2025-10-25',10),('W001','TC03','Mèo Anh lông ngắn','2025-10-25',14),('W001','TC04','Mèo Ba Tư','2025-10-25',2),('W001','TC05','Chuột Hamster Bear','2025-10-25',2),('W001','TC06','Thỏ cảnh Mini Rex','2025-10-25',2),('W001','TC07','Cá Betta (cá xiêm)','2025-10-25',2),('W001','TC08','Chó Shiba','2025-10-25',2),('W001','TC09','Vẹt Cockatiel','2025-10-25',2),('W001','TC10','Rùa cảnh Sulcata','2025-10-25',2),('W001','TC6111','Mèo ta','2025-10-25',2),('W001','TC6971','Chó Samoyed','2025-10-25',2),('W001','TC7307','Mèo Anh lông dài','2025-10-25',2),('W009','DD01','Lồng nuôi Hamster','2025-10-01',10),('W009','TC04','Mèo Ba Tư','2025-09-06',14),('W009','TC05','Chuột Hamster Bear','2025-09-07',40),('W009','TC06','Thỏ cảnh Mini Rex','2025-09-08',5),('W009','TC07','Cá Betta (cá xiêm)','2025-09-09',18),('W009','TC08','Cá Koi Nhật','2025-09-10',7),('W009','TC6111','Mèo ta','2025-10-04',17),('W010','DD04','Đệm ngủ cho chó mèo','2025-10-08',20),('W010','DD05','Áo quần cho chó nhỏ','2025-10-08',20),('W010','DD06','Vòng cổ chống ve','2025-10-08',15),('W010','TC02','Chó Corgi','2025-09-15',4),('W010','TC09','Vet Cockatiel','2025-09-11',5),('W010','TC7307','Mèo Anh lông dài','2025-09-13',8),('W2735','DD9954','Cát than hoạt tính','2025-10-16',10),('W2735','TC01','Chó Poodle mini','2025-10-16',10),('W3870','DD05','Áo quần cho chó nhỏ','2025-10-02',6),('W3870','DD06','Vòng cổ chống ve','2025-10-02',9),('W3870','DD07','Dây dắt chó ','2025-10-02',10),('W3870','DD08','Thức ăn hạt cho mèo (2kg)','2025-10-02',112),('W6884','DD04','Đệm ngủ cho chó mèo','2025-10-01',22),('W6884','DD05','Áo quần cho chó nhỏ','2025-10-01',42),('W7724','DD09','Thức ăn hạt cho chó (5kg)','2025-10-14',15),('W7724','DD10','Bể cá thủy sinh mini','2025-10-14',20),('W7724','DD8922','Chuồng chó inox','2025-10-14',20),('W997','DD02','Chuồng chó inox','2025-10-24',1),('W997','DD03','Khay vệ sinh cho mèo','2025-10-24',40),('W997','DD04','Đệm ngủ cho chó mèo','2025-10-24',130),('W997','DD09','Thức ăn hạt cho chó (5kg)','2025-07-23',0),('W997','DD10','Bể cá thủy sinh mini','2025-10-01',9),('W997','TC03','Mèo Anh lông ngắn','2025-10-14',1),('W997','TC08','Cá Koi Nhật','2025-09-30',3),('W997','TC09','Vẹt Cockatiel','2025-10-14',2),('W997','TC10','Rùa cảnh Sulcata','2025-10-05',7);
/*!40000 ALTER TABLE `warehousedetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-06 16:28:00
