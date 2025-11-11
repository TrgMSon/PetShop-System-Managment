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
  `idCustomer` char(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `phone` char(10) DEFAULT NULL,
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
INSERT INTO `customer` VALUES ('C0001','Nguyễn Văn Anh','1234567893'),('C0002','Trần Thị Bình','1237654666'),('C0003','Lê Văn Cường','3112233222'),('C0004','Phạm Thị Dung','3333346666'),('C0006','Nguyễn Văn A','1243425555'),('C0007','Đỗ Văn Minh','5652335555'),('C0008','Bùi Thị Hương','5554533333'),('C0009','Phan Văn Nam','7711993333'),('C0010','Nguyễn Thị Oanh','1238113333'),('C0011','Trần Văn Phúc','2227332222'),('C0012','Lê Thị Quyên','1115611111'),('C0013','Phạm Văn Sơn','0003211111'),('C0015','Hoàng Văn Tuấn','0004611111'),('C0016','Đỗ Thị Uyên','0101331111'),('C0017','Bùi Văn Việt','1106771111'),('C0018','Phan Thị Xuân','0027111111'),('C0019','Nguyễn Thị Yến','0029881111'),('C0020','Trần Thị Linh','0013991111'),('C0496','Nguyễn Văn A','1234214111'),('C0700','Lê Văn Bình','1423423111'),('C1067','An','1242311111'),('C3226','Lê Văn Cường','1234567890'),('C3538','An','1234234111'),('C3769','Nguyễn Nam Anh','1242342311'),('C3926','Hải','1111111111'),('C4380','An','1234567891'),('C5278','Hải','8888882222'),('C5402','Nguyễn Anh','4352345522'),('C6060','An','1354322222'),('C6360','Nguyễn Văn A','1231453222'),('C6886','An','1421342222'),('C6939','Nguyễn Văn Anh','1247235433'),('C7623','Trương Minh Sơn','9876543210'),('C8065','Phan Thị Xuân','0027111112'),('C8331','An','1342345555'),('C8663','Phạm Hải','1425234555'),('C8936','Hải','8888888888'),('C9009','Nguyễn Hoàng Hà','9723755555'),('C9590','An','1234144444');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `idInvoice` char(10) NOT NULL,
  `totalAmount` decimal(10,0) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `idCustomer` char(10) NOT NULL,
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
INSERT INTO `invoice` VALUES ('I0060',600000,'2025-10-04','C9590'),('I0078',6410000,'2025-10-25','C7623'),('I0133',5000000,'2025-11-09','C8936'),('I0848',35400000,'2025-01-08','C8936'),('I0986',8000000,'2025-10-09','C0001'),('I1315',5900000,'2025-11-08','C8936'),('I1416',30000000,'2025-06-09','C8936'),('I1458',7320000,'2025-10-23','C3926'),('I1586',13300000,'2025-10-24','C8065'),('I1621',250000,'2025-10-24','C0003'),('I1938',600000,'2025-10-04','C9590'),('I1945',8800000,'2025-11-08','C8936'),('I2140',17500000,'2025-10-05','C8663'),('I2626',12900000,'2025-11-08','C8936'),('I2763',60000000,'2025-11-08','C8936'),('I2987',9000000,'2025-10-05','C8663'),('I3190',6500000,'2025-09-24','C0001'),('I3348',400000,'2025-10-04','C9590'),('I3502',400000,'2025-10-04','C9590'),('I4065',2500000,'2025-10-05','C0700'),('I4125',3500000,'2025-10-05','C8663'),('I4476',6300000,'2025-08-26','C8936'),('I5148',400000,'2025-10-04','C9590'),('I5240',5000000,'2025-11-09','C8936'),('I5322',1000000,'2025-10-04','C9590'),('I5881',12000000,'2025-10-16','C8936'),('I6676',20000000,'2025-07-08','C8936'),('I7056',5000000,'2025-11-09','C8936'),('I7198',58500000,'2025-10-05','C8663'),('I7323',4000000,'2025-10-04','C9590'),('I7516',12500000,'2025-10-24','C0003'),('I7596',60000000,'2025-11-08','C8936'),('I7781',3500000,'2025-09-26','C0001'),('I8640',14000000,'2025-10-08','C8936'),('I9106',3500000,'2025-10-05','C8663'),('I9133',1690000,'2025-10-23','C3926'),('I9982',3500000,'2025-10-05','C8663');
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
  `idProduct` char(10) NOT NULL,
  `idInvoice` char(10) NOT NULL,
  PRIMARY KEY (`idInvoice`,`idProduct`),
  KEY `idProduct_idx` (`idProduct`),
  CONSTRAINT `idInvoice` FOREIGN KEY (`idInvoice`) REFERENCES `invoice` (`idInvoice`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idProduct` FOREIGN KEY (`idProduct`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoicedetail`
--

LOCK TABLES `invoicedetail` WRITE;
/*!40000 ALTER TABLE `invoicedetail` DISABLE KEYS */;
INSERT INTO `invoicedetail` VALUES (3,'TC6111','I0060'),(3,'DD0005','I0078'),(11,'DD0009','I0078'),(20,'DD0001','I0133'),(3,'DD0008','I0848'),(3,'DD0009','I0848'),(1,'TC6971','I0848'),(2,'TC7307','I0848'),(10,'DD0008','I0986'),(10,'DD0009','I0986'),(1,'DD0007','I1315'),(2,'DD0008','I1315'),(3,'DD0009','I1315'),(4,'DD0010','I1315'),(1,'TC0008','I1416'),(1,'DD0005','I1458'),(1,'DD0006','I1458'),(1,'TC0004','I1458'),(10,'DD0003','I1586'),(20,'DD0008','I1586'),(1,'TC0003','I1586'),(1,'DD0001','I1621'),(3,'TC6111','I1938'),(1,'TC0009','I1945'),(2,'TC0010','I1945'),(5,'TC0010','I2140'),(10,'DD0206','I2626'),(10,'DD8922','I2626'),(2,'TC0008','I2763'),(5,'TC0009','I2987'),(1,'TC7307','I3190'),(2,'TC6111','I3348'),(2,'TC6111','I3502'),(10,'DD0008','I4065'),(1,'TC0010','I4125'),(1,'DD0006','I4476'),(2,'DD0009','I4476'),(1,'TC0001','I4476'),(2,'TC6111','I5148'),(20,'DD0001','I5240'),(5,'TC6111','I5322'),(10,'DD8922','I5881'),(1,'TC6971','I6676'),(20,'DD0001','I7056'),(13,'TC0010','I7198'),(2,'TC7307','I7198'),(20,'TC6111','I7323'),(50,'DD0008','I7516'),(2,'TC0008','I7596'),(1,'TC0010','I7781'),(10,'DD0206','I8640'),(10,'DD8922','I8640'),(10,'DD9954','I8640'),(1,'TC0010','I9106'),(1,'DD0009','I9133'),(1,'DD0010','I9133'),(2,'TC0007','I9133'),(1,'TC0010','I9982');
/*!40000 ALTER TABLE `invoicedetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `idProduct` char(10) NOT NULL,
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
INSERT INTO `product` VALUES ('DD0001','Lồng nuôi Hamster',250000,'Trung Quốc','Đồ dùng cho thú cưng'),('DD0002','Chuồng chó inox',1200000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0003','Khay vệ sinh cho mèo',180000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0004','Đệm ngủ cho chó mèo',300000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0005','Áo quần cho chó nhỏ',120000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0006','Vòng cổ chống ve',200000,'Đức','Đồ dùng cho thú cưng'),('DD0007','Dây dắt chó ',150000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0008','Thức ăn hạt cho mèo (2kg)',250000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0009','Thức ăn hạt cho chó (5kg)',550000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0010','Bể cá thủy sinh mini',900000,'Việt Nam','Đồ dùng cho thú cưng'),('DD0206','Xà phòng tắm',90000,'Việt Nam','Đồ dùng cho thú cưng'),('DD8922','Chuồng chó inox',1200000,'Trung Quốc','Đồ dùng cho thú cưng'),('DD9954','Cát than hoạt tính',110000,'Việt Nam','Đồ dùng cho thú cưng'),('TC0001','Chó Poodle mini',5000000,'Việt Nam','Thú cưng'),('TC0002','Chó Corgi',12000000,'Thái Lan','Thú cưng'),('TC0003','Mèo Anh lông ngắn',6500000,'Anh','Thú cưng'),('TC0004','Mèo Ba Tư',7000000,'Iran','Thú cưng'),('TC0005','Chuột Hamster Bear',150000,'Việt Nam','Thú cưng'),('TC0006','Thỏ cảnh Mini Rex',800000,'Hà Lan','Thú cưng'),('TC0007','Cá Betta (cá xiêm)',120000,'Thái Lan','Thú cưng'),('TC0008','Chó Shiba',30000000,'Nhật Bản','Thú cưng'),('TC0009','Vẹt Cockatiel',1800000,'Úc','Thú cưng'),('TC0010','Rùa cảnh Sulcata',3500000,'Châu Phi','Thú cưng'),('TC6111','Mèo ta',200000,'Việt Nam','Thú cưng'),('TC6971','Chó Samoyed',20000000,'Phần Lan','Thú cưng'),('TC7307','Mèo Anh lông dài',6500000,'Anh','Thú cưng');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `idWarehouse` char(10) NOT NULL,
  `address` varchar(45) NOT NULL,
  `maxCapacity` int DEFAULT NULL,
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
INSERT INTO `warehouse` VALUES ('W0001','Hà Nội',2000),('W0009','Bắc Ninh',1500),('W0010','Hải Phòng',2000),('W0997','Hà Nội',1000),('W2735','Đà Nẵng',1000),('W3870','Bắc Ninh',2000),('W6884','Hà Nội',2000),('W7724','Đà Nẵng',1000);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehousedetail`
--

DROP TABLE IF EXISTS `warehousedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehousedetail` (
  `idWarehouse` char(10) NOT NULL,
  `idProductW` char(10) NOT NULL,
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
INSERT INTO `warehousedetail` VALUES ('W0001','DD0001','2025-10-25',0),('W0001','DD0002','2025-10-25',2),('W0001','DD0003','2025-10-25',2),('W0001','DD0004','2025-10-25',1),('W0001','DD0005','2025-10-25',0),('W0001','DD0006','2025-10-25',0),('W0001','DD0007','2025-10-25',0),('W0001','DD0008','2025-10-25',0),('W0001','DD0009','2025-10-25',52),('W0001','DD0010','2025-10-25',0),('W0001','DD0206','2025-10-25',0),('W0001','DD8922','2025-10-25',0),('W0001','DD9954','2025-10-25',0),('W0001','TC0001','2025-11-08',112),('W0001','TC0002','2025-11-08',110),('W0001','TC0003','2025-11-08',114),('W0001','TC0004','2025-11-08',102),('W0001','TC0005','2025-11-08',52),('W0001','TC0006','2025-11-08',52),('W0001','TC0007','2025-11-08',52),('W0001','TC0008','2025-11-08',52),('W0001','TC0009','2025-11-08',51),('W0001','TC0010','2025-11-08',50),('W0001','TC6111','2025-11-08',52),('W0001','TC6971','2025-11-08',52),('W0001','TC7307','2025-11-08',52),('W0009','DD0001','2025-10-01',0),('W0009','DD0008','2025-11-07',200),('W0009','DD0009','2025-11-07',200),('W0009','DD0010','2025-11-07',200),('W0009','DD8922','2025-11-07',50),('W0009','DD9954','2025-11-07',50),('W0009','TC0001','2025-11-07',50),('W0009','TC0002','2025-11-07',50),('W0009','TC0004','2025-09-06',14),('W0009','TC0005','2025-09-07',40),('W0009','TC0006','2025-09-08',5),('W0009','TC0007','2025-09-09',18),('W0009','TC0008','2025-09-10',1),('W0009','TC6111','2025-10-04',17),('W0010','DD0001','2025-11-08',30),('W0010','DD0002','2025-11-08',50),('W0010','DD0003','2025-11-08',50),('W0010','DD0004','2025-11-08',70),('W0010','DD0005','2025-11-08',70),('W0010','DD0006','2025-11-08',65),('W0010','DD0007','2025-11-08',50),('W0010','DD0008','2025-11-08',50),('W0010','DD0009','2025-11-08',50),('W0010','DD0010','2025-11-08',50),('W0010','DD0206','2025-11-08',50),('W0010','DD8922','2025-11-08',50),('W0010','DD9954','2025-11-08',50),('W0010','TC0001','2025-11-08',50),('W0010','TC0002','2025-11-08',54),('W0010','TC0003','2025-11-08',50),('W0010','TC0004','2025-11-08',50),('W0010','TC0005','2025-11-08',50),('W0010','TC0006','2025-11-08',50),('W0010','TC0009','2025-09-11',5),('W0010','TC7307','2025-09-13',6),('W0997','DD0001','2025-11-08',0),('W0997','DD0002','2025-09-05',31),('W0997','DD0003','2025-09-05',80),('W0997','DD0004','2025-09-05',170),('W0997','DD0005','2025-09-05',40),('W0997','DD0006','2025-09-05',39),('W0997','DD0007','2025-08-10',40),('W0997','DD0008','2025-11-08',20),('W0997','DD0009','2025-11-08',15),('W0997','DD0010','2025-08-10',35),('W0997','DD0206','2025-11-08',3),('W0997','DD8922','2025-08-10',22),('W0997','DD9954','2025-08-10',24),('W0997','TC0001','2025-11-08',19),('W0997','TC0002','2025-11-08',20),('W0997','TC0003','2025-11-08',21),('W0997','TC0004','2025-11-08',20),('W0997','TC0005','2025-11-08',20),('W0997','TC0006','2025-11-08',20),('W0997','TC0007','2025-11-08',20),('W0997','TC0008','2025-11-08',23),('W0997','TC0009','2025-11-08',22),('W0997','TC0010','2025-11-08',27),('W0997','TC6111','2025-11-08',20),('W0997','TC6971','2025-11-08',17),('W0997','TC7307','2025-11-08',20),('W2735','DD0008','2025-11-08',7),('W2735','DD0009','2025-11-08',110),('W2735','DD0010','2025-11-05',100),('W2735','DD0206','2025-11-08',2),('W2735','DD8922','2025-11-05',100),('W2735','DD9954','2025-11-05',110),('W2735','TC0003','2025-11-05',150),('W2735','TC0004','2025-11-05',150),('W2735','TC0005','2025-11-05',100),('W2735','TC6971','2025-11-08',50),('W2735','TC7307','2025-11-08',50),('W3870','DD0001','2025-11-06',100),('W3870','DD0002','2025-11-06',100),('W3870','DD0003','2025-11-06',100),('W3870','DD0004','2025-11-06',100),('W3870','DD0005','2025-11-06',106),('W3870','DD0006','2025-11-06',109),('W3870','DD0007','2025-11-06',108),('W3870','DD0008','2025-11-06',210),('W3870','DD0009','2025-11-06',100),('W3870','DD0010','2025-11-06',100),('W3870','DD0206','2025-11-06',100),('W3870','DD8922','2025-11-06',100),('W3870','DD9954','2025-11-06',100),('W6884','DD0001','2025-11-02',50),('W6884','DD0002','2025-11-02',50),('W6884','DD0003','2025-11-02',50),('W6884','DD0004','2025-11-02',72),('W6884','DD0005','2025-11-02',92),('W6884','DD0006','2025-11-02',50),('W6884','DD0007','2025-11-02',50),('W6884','DD0008','2025-11-02',50),('W6884','DD0009','2025-11-02',50),('W6884','DD0010','2025-11-02',50),('W6884','DD0206','2025-11-02',50),('W6884','DD8922','2025-11-02',50),('W6884','DD9954','2025-11-02',50),('W6884','TC0001','2025-11-02',50),('W6884','TC0002','2025-11-02',50),('W6884','TC0003','2025-11-02',50),('W6884','TC0004','2025-11-02',50),('W6884','TC0005','2025-11-02',50),('W6884','TC0006','2025-11-02',50),('W6884','TC0007','2025-11-02',50),('W6884','TC0008','2025-11-02',50),('W6884','TC0009','2025-11-02',50),('W6884','TC0010','2025-11-02',50),('W6884','TC6111','2025-11-02',50),('W6884','TC6971','2025-11-02',50),('W6884','TC7307','2025-11-02',50),('W7724','DD0001','2025-11-08',50),('W7724','DD0002','2025-11-08',50),('W7724','DD0003','2025-11-08',50),('W7724','DD0004','2025-11-08',50),('W7724','DD0005','2025-11-08',50),('W7724','DD0006','2025-11-08',50),('W7724','DD0007','2025-11-08',50),('W7724','DD0008','2025-11-08',50),('W7724','DD0009','2025-11-08',62),('W7724','DD0010','2025-11-08',70),('W7724','DD0206','2025-11-08',50),('W7724','DD8922','2025-11-08',70),('W7724','DD9954','2025-11-08',50);
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

-- Dump completed on 2025-11-11 20:40:14
