-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 25, 2015 at 04:59 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dianjayan`
--
CREATE DATABASE IF NOT EXISTS `dianjayan` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dianjayan`;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `idCategory` varchar(45) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `category`
--

TRUNCATE TABLE `category`;
--
-- Dumping data for table `category`
--

INSERT INTO `category` (`idCategory`, `Name`) VALUES
('DJIC001', 'GRC Board'),
('DJIC002', 'JayaBoard');

-- --------------------------------------------------------

--
-- Table structure for table `consumer`
--

DROP TABLE IF EXISTS `consumer`;
CREATE TABLE IF NOT EXISTS `consumer` (
  `idconsumer` varchar(45) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `Type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `consumer`
--

TRUNCATE TABLE `consumer`;
--
-- Dumping data for table `consumer`
--

INSERT INTO `consumer` (`idconsumer`, `name`, `address`, `phone`, `Type`) VALUES
('DJC001', 'Michael', 'Kav Polri Blok A7 No 178 B Jelambar', '08988199920', 'Retail');

-- --------------------------------------------------------

--
-- Table structure for table `distributor`
--

DROP TABLE IF EXISTS `distributor`;
CREATE TABLE IF NOT EXISTS `distributor` (
  `iddistributor` varchar(45) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `distributor`
--

TRUNCATE TABLE `distributor`;
--
-- Dumping data for table `distributor`
--

INSERT INTO `distributor` (`iddistributor`, `name`, `address`, `phone`) VALUES
('DJD001', 'GRC BOARD', 'Graha GRC board Lt.3\nJl. S.Parman Kav.64\nJakarta 11410 - Indonesia', '53666800');

-- --------------------------------------------------------

--
-- Table structure for table `hist_price`
--

DROP TABLE IF EXISTS `hist_price`;
CREATE TABLE IF NOT EXISTS `hist_price` (
  `idHist` varchar(45) NOT NULL,
  `inventory_idinventory` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `before` double DEFAULT NULL,
  `after` double DEFAULT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `hist_price`
--

TRUNCATE TABLE `hist_price`;
--
-- Dumping data for table `hist_price`
--

INSERT INTO `hist_price` (`idHist`, `inventory_idinventory`, `status`, `before`, `after`, `date`) VALUES
('DJH001', 'DJI002', 'Normal', 1, 1, '2015-08-25 14:01:45'),
('DJH002', 'DJI003', 'Normal', 11, 11, '2015-08-25 14:40:46');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `idinventory` varchar(45) NOT NULL,
  `idCategory` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `price_Sale` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `inventory`
--

TRUNCATE TABLE `inventory`;
--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`idinventory`, `idCategory`, `name`, `qty`, `unit`, `price_Sale`) VALUES
('DJI001', 'DJIC001', 'Test1', NULL, 'Lbr', 2000),
('DJI002', 'DJIC001', 'q', NULL, 'w', 1),
('DJI003', 'DJIC001', 'q1', NULL, 'lbr', 11);

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE IF NOT EXISTS `log` (
  `idlog` int(11) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `ip_log` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `log`
--

TRUNCATE TABLE `log`;
--
-- Dumping data for table `log`
--

INSERT INTO `log` (`idlog`, `username`, `date`, `ip_log`, `status`) VALUES
(1, '1', '2015-08-25 11:45:25', '139.0.15.66', 'Sukses'),
(2, '1', '2015-08-25 11:46:40', '202.146.254.78', 'Sukses'),
(3, '1', '2015-08-25 12:00:36', '202.146.254.78', 'Sukses'),
(4, '1', '2015-08-25 12:17:05', '202.146.254.78', 'Sukses'),
(5, '1', '2015-08-25 12:20:24', '139.0.15.66', 'Sukses'),
(6, '1', '2015-08-25 12:26:32', '139.0.15.66', 'Sukses'),
(7, '1', '2015-08-25 12:41:09', '139.0.15.66', 'Sukses'),
(8, '1', '2015-08-25 12:44:14', '139.0.15.66', 'Sukses'),
(9, '1', '2015-08-25 12:47:43', '139.0.15.66', 'Sukses'),
(10, '1', '2015-08-25 12:49:22', '139.0.15.66', 'Sukses'),
(11, '', '2015-08-25 12:50:45', '202.146.254.78', 'Failed'),
(12, '1', '2015-08-25 12:50:48', '202.146.254.78', 'Sukses'),
(13, '1', '2015-08-25 12:53:15', '139.0.15.66', 'Sukses'),
(14, '1', '2015-08-25 12:54:28', '202.146.254.78', 'Sukses'),
(15, '1', '2015-08-25 12:55:03', '202.146.254.78', 'Sukses'),
(16, '1', '2015-08-25 13:42:09', '116.50.25.12', 'Sukses'),
(17, '1', '2015-08-25 13:45:31', '116.50.25.12', 'Sukses'),
(18, '1', '2015-08-25 13:55:16', '116.50.25.12', 'Sukses'),
(19, '1', '2015-08-25 13:56:59', '116.50.25.12', 'Sukses'),
(20, '1', '2015-08-25 14:31:27', '116.50.25.12', 'Sukses'),
(21, '1', '2015-08-25 14:40:28', '116.50.25.12', 'Sukses');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `idpayment` varchar(45) NOT NULL,
  `Type_Payment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `payment`
--

TRUNCATE TABLE `payment`;
--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`idpayment`, `Type_Payment`) VALUES
('DJTP01', 'Kartu Kredit');

-- --------------------------------------------------------

--
-- Table structure for table `po_sales_detail`
--

DROP TABLE IF EXISTS `po_sales_detail`;
CREATE TABLE IF NOT EXISTS `po_sales_detail` (
  `no` int(11) NOT NULL,
  `idsales` varchar(255) NOT NULL,
  `idinventory` varchar(45) NOT NULL,
  `qtyreturn` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `fqty` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `Discount` double DEFAULT NULL,
  `profit_pcs` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `po_sales_detail`
--

TRUNCATE TABLE `po_sales_detail`;
-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `idpurchase` varchar(45) NOT NULL,
  `idusername` varchar(45) NOT NULL,
  `idpayment` varchar(45) DEFAULT NULL,
  `iddistributor` varchar(45) DEFAULT NULL,
  `Total_Payment` double DEFAULT NULL,
  `Total_QTY` int(11) DEFAULT NULL,
  `Total_QTY_Return` int(11) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Date` datetime DEFAULT NULL,
  `Comment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `purchase`
--

TRUNCATE TABLE `purchase`;
-- --------------------------------------------------------

--
-- Table structure for table `purchase_detail`
--

DROP TABLE IF EXISTS `purchase_detail`;
CREATE TABLE IF NOT EXISTS `purchase_detail` (
  `no` int(11) NOT NULL,
  `idpurchase` varchar(45) NOT NULL,
  `idinventory` varchar(45) NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `qtyreturn` varchar(45) DEFAULT NULL,
  `fqty` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `purchase_detail`
--

TRUNCATE TABLE `purchase_detail`;
-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
CREATE TABLE IF NOT EXISTS `sales` (
  `idsales` varchar(255) NOT NULL,
  `idusername` varchar(45) NOT NULL,
  `idconsumer` varchar(45) DEFAULT NULL,
  `idpayment` varchar(45) DEFAULT NULL,
  `iddistributor` varchar(45) DEFAULT NULL,
  `Biaya_Kirim` int(11) DEFAULT NULL,
  `Discount` double DEFAULT NULL,
  `Total_QTY` int(11) DEFAULT NULL,
  `Total_QTY_Return` int(11) DEFAULT NULL,
  `Total_Payment` double DEFAULT NULL,
  `Laba` double DEFAULT NULL,
  `Date` datetime DEFAULT NULL,
  `Comment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `sales`
--

TRUNCATE TABLE `sales`;
-- --------------------------------------------------------

--
-- Table structure for table `sales_detail`
--

DROP TABLE IF EXISTS `sales_detail`;
CREATE TABLE IF NOT EXISTS `sales_detail` (
  `no` int(11) NOT NULL,
  `idsales` varchar(255) NOT NULL,
  `idinventory` varchar(45) NOT NULL,
  `qtyreturn` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `fqty` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `Discount` double DEFAULT NULL,
  `profit_pcs` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `sales_detail`
--

TRUNCATE TABLE `sales_detail`;
-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `idusername` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `date_join` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `user`
--

TRUNCATE TABLE `user`;
--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idusername`, `username`, `password`, `name`, `telephone`, `date_join`) VALUES
('DJU001', '1', 'c4ca4238a0b923820dcc509a6f75849b', 'Michael', '08988199920', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idCategory`);

--
-- Indexes for table `consumer`
--
ALTER TABLE `consumer`
  ADD PRIMARY KEY (`idconsumer`);

--
-- Indexes for table `distributor`
--
ALTER TABLE `distributor`
  ADD PRIMARY KEY (`iddistributor`);

--
-- Indexes for table `hist_price`
--
ALTER TABLE `hist_price`
  ADD PRIMARY KEY (`idHist`,`inventory_idinventory`), ADD KEY `fk_hist_price_inventory1_idx` (`inventory_idinventory`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`idinventory`), ADD KEY `fk_inventory_Category1_idx` (`idCategory`);

--
-- Indexes for table `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`idlog`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`idpayment`);

--
-- Indexes for table `po_sales_detail`
--
ALTER TABLE `po_sales_detail`
  ADD PRIMARY KEY (`no`), ADD KEY `fk_sales_detail_sales1_idx` (`idsales`), ADD KEY `fk_sales_detail_inventory1_idx` (`idinventory`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`idpurchase`), ADD KEY `fk_purchase_user1_idx` (`idusername`), ADD KEY `fk_purchase_distributor1_idx` (`iddistributor`), ADD KEY `fk_purchase_payment1_idx` (`idpayment`);

--
-- Indexes for table `purchase_detail`
--
ALTER TABLE `purchase_detail`
  ADD PRIMARY KEY (`no`), ADD KEY `fk_purchase_detail_purchase1_idx` (`idpurchase`), ADD KEY `fk_purchase_detail_inventory1_idx` (`idinventory`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`idsales`), ADD KEY `fk_sales_user_idx` (`idusername`), ADD KEY `fk_sales_consumer1_idx` (`idconsumer`), ADD KEY `fk_sales_payment1_idx` (`idpayment`), ADD KEY `fk_sales_distributor1_idx` (`iddistributor`);

--
-- Indexes for table `sales_detail`
--
ALTER TABLE `sales_detail`
  ADD PRIMARY KEY (`no`), ADD KEY `fk_sales_detail_sales1_idx` (`idsales`), ADD KEY `fk_sales_detail_inventory1_idx` (`idinventory`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idusername`,`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `log`
--
ALTER TABLE `log`
  MODIFY `idlog` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `po_sales_detail`
--
ALTER TABLE `po_sales_detail`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `purchase_detail`
--
ALTER TABLE `purchase_detail`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `sales_detail`
--
ALTER TABLE `sales_detail`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `hist_price`
--
ALTER TABLE `hist_price`
ADD CONSTRAINT `fk_hist_price_inventory1` FOREIGN KEY (`inventory_idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
ADD CONSTRAINT `fk_inventory_Category1` FOREIGN KEY (`idCategory`) REFERENCES `category` (`idCategory`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `po_sales_detail`
--
ALTER TABLE `po_sales_detail`
ADD CONSTRAINT `fk_sales_detail_inventory10` FOREIGN KEY (`idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_sales_detail_sales10` FOREIGN KEY (`idsales`) REFERENCES `sales` (`idsales`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
ADD CONSTRAINT `fk_purchase_distributor1` FOREIGN KEY (`iddistributor`) REFERENCES `distributor` (`iddistributor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_purchase_payment1` FOREIGN KEY (`idpayment`) REFERENCES `payment` (`idpayment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_purchase_user1` FOREIGN KEY (`idusername`) REFERENCES `user` (`idusername`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `purchase_detail`
--
ALTER TABLE `purchase_detail`
ADD CONSTRAINT `fk_purchase_detail_inventory1` FOREIGN KEY (`idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_purchase_detail_purchase1` FOREIGN KEY (`idpurchase`) REFERENCES `purchase` (`idpurchase`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
ADD CONSTRAINT `fk_sales_consumer1` FOREIGN KEY (`idconsumer`) REFERENCES `consumer` (`idconsumer`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_sales_distributor1` FOREIGN KEY (`iddistributor`) REFERENCES `distributor` (`iddistributor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_sales_payment1` FOREIGN KEY (`idpayment`) REFERENCES `payment` (`idpayment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_sales_user` FOREIGN KEY (`idusername`) REFERENCES `user` (`idusername`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `sales_detail`
--
ALTER TABLE `sales_detail`
ADD CONSTRAINT `fk_sales_detail_inventory1` FOREIGN KEY (`idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_sales_detail_sales1` FOREIGN KEY (`idsales`) REFERENCES `sales` (`idsales`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
