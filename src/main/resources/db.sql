-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: appdata-mysql.clygu27w06nt.us-west-2.rds.amazonaws.com:3306
-- Generation Time: Jul 28, 2016 at 10:14 PM
-- Server version: 5.7.11
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appdata`
--
CREATE DATABASE IF NOT EXISTS `appdata` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `appdata`;

-- --------------------------------------------------------

--
-- Table structure for table `alexa_brasil`
--

CREATE TABLE `alexa_brasil` (
  `domain` varchar(200) COLLATE utf8_bin NOT NULL,
  `language` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `global_rank` int(20) DEFAULT NULL,
  `medium_time` int(20) DEFAULT NULL,
  `sufix` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `rank` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sub_category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `whoxy_whois` text COLLATE utf8_bin,
  `processed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `app_store`
--

CREATE TABLE `app_store` (
  `position` int(20) DEFAULT NULL,
  `url` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `developername` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `developerurl` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `isfree` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `category` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `updatedate` date DEFAULT NULL,
  `version` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `languages` varchar(3000) COLLATE utf8_bin DEFAULT NULL,
  `starscurrversion` int(20) DEFAULT NULL,
  `starsallversions` int(20) DEFAULT NULL,
  `ratingcurrversion` int(20) DEFAULT NULL,
  `ratingallversions` int(20) DEFAULT NULL,
  `developerwebsite` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `supportwebsite` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `description` text COLLATE utf8_bin
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `ecommerce`
--

CREATE TABLE `ecommerce` (
  `cnpj` char(14) COLLATE utf8_bin NOT NULL,
  `cnae_primario` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `cnae_descricao` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `razao_social` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `endereco` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `bairro` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `cidade` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `cep` char(8) COLLATE utf8_bin DEFAULT NULL,
  `total_vendido` decimal(20,2) DEFAULT '0.00'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `play_store`
--

CREATE TABLE `play_store` (
  `position` int(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `appid` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `referencedate` date DEFAULT NULL,
  `developer` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `istopdeveloper` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `developerurl` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerdomain` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `isfree` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `score_total` decimal(20,5) DEFAULT NULL,
  `score_count` int(20) DEFAULT NULL,
  `score_fivestars` int(20) DEFAULT NULL,
  `score_fourstars` int(20) DEFAULT NULL,
  `score_threestars` int(20) DEFAULT NULL,
  `score_twostars` int(20) DEFAULT NULL,
  `score_onestars` int(20) DEFAULT NULL,
  `instalations` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `currentversion` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `haveinapppurchases` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `developeremail` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerwebsite` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `physicaladdress` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `lastupdatedate` date DEFAULT NULL,
  `description` text COLLATE utf8_bin
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `whois`
--

CREATE TABLE `whois` (
  `original_domain` varchar(200) COLLATE utf8_bin NOT NULL,
  `domain_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `query_time` date DEFAULT NULL,
  `whois_server` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `domain_registered` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `domain_registrar_iana_id` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `domain_registrar_registrar_name` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `domain_registrar_whois_server` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `domain_registrar_website_url` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `domain_registrar_email_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `domain_registrar_phone_number` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_full_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_mailing_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_city_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_state_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_zip_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_country_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_country_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_email_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_phone_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `registrant_contact_fax_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_mailing_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_city_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_state_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_zip_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_country_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_country_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_email_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_phone_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `administrative_contact_fax_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_full_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_mailing_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_city_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_state_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_zip_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_country_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_country_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_email_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_phone_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `technical_contact_fax_number` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_mailing_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_city_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_state_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_zip_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_country_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_country_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_email_address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_phone_number` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `billing_contact_fax_number` varchar(50) COLLATE utf8_bin DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alexa_brasil`
--
ALTER TABLE `alexa_brasil`
  ADD PRIMARY KEY (`domain`);

--
-- Indexes for table `ecommerce`
--
ALTER TABLE `ecommerce`
  ADD PRIMARY KEY (`cnpj`);

--
-- Indexes for table `whois`
--
ALTER TABLE `whois`
  ADD PRIMARY KEY (`original_domain`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
