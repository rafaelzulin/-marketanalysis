-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: appdata-mysql.clygu27w06nt.us-west-2.rds.amazonaws.com:3306
-- Generation Time: 11-Ago-2016 às 20:06
-- Versão do servidor: 5.7.11
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

-- --------------------------------------------------------

--
-- Estrutura da tabela `alexa_brasil`
--

CREATE TABLE `alexa_brasil` (
  `domain` varchar(200) COLLATE utf8_bin NOT NULL,
  `language` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `global_rank` int(20) DEFAULT NULL,
  `medium_time` int(20) DEFAULT NULL,
  `category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sub_category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `whoxy_whois` text COLLATE utf8_bin,
  `processed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `app_store`
--

CREATE TABLE `app_store` (
  `url` varchar(250) COLLATE utf8_bin NOT NULL,
  `name` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `developername` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `developerurl` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
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
  `import_time` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `app_store_developers`
--

CREATE TABLE `app_store_developers` (
  `developername` char(250) COLLATE utf8_bin NOT NULL,
  `developerurl` char(250) COLLATE utf8_bin DEFAULT NULL,
  `developerwebsite` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `supportwebsite` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `languages` varchar(3000) COLLATE utf8_bin DEFAULT NULL,
  `sum_starsallversions` int(11) DEFAULT NULL,
  `sum_ratingallversions` int(11) DEFAULT NULL,
  `qty_apps` int(11) DEFAULT NULL,
  `processed` tinyint(1) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ebit`
--

CREATE TABLE `ebit` (
  `nome` varchar(100) COLLATE utf8_bin NOT NULL,
  `mercado` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `medalha` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `avaliacoes` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `site` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `status_site` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `import_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ecommerce`
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
-- Estrutura da tabela `linkedin`
--

CREATE TABLE `linkedin` (
  `company_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `domain` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `industry` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `employees` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `locality` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `region` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `country` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `social_network_link` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `website` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(2001) COLLATE utf8_bin DEFAULT NULL,
  `street` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `postal_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `year_established` varchar(10) COLLATE utf8_bin DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `play_store`
--

CREATE TABLE `play_store` (
  `appid` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `referencedate` date DEFAULT NULL,
  `developer` varchar(320) COLLATE utf8_bin DEFAULT NULL,
  `istopdeveloper` tinyint(1) NOT NULL DEFAULT '0',
  `developerurl` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerdomain` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `category` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `score_total` decimal(20,5) DEFAULT NULL,
  `score_count` int(20) DEFAULT NULL,
  `score_fivestars` int(20) DEFAULT NULL,
  `score_fourstars` int(20) DEFAULT NULL,
  `score_threestars` int(20) DEFAULT NULL,
  `score_twostars` int(20) DEFAULT NULL,
  `score_onestars` int(20) DEFAULT NULL,
  `instalations` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `haveinapppurchases` tinyint(1) NOT NULL DEFAULT '0',
  `developeremail` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerwebsite` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `physicaladdress` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `lastupdatedate` date DEFAULT NULL,
  `import_time` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `play_store_developers`
--

CREATE TABLE `play_store_developers` (
  `developer` varchar(320) COLLATE utf8_bin NOT NULL,
  `istopdeveloper` tinyint(1) DEFAULT NULL,
  `developerurl` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerdomain` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `score_total` decimal(20,5) DEFAULT NULL,
  `score_count` int(20) DEFAULT NULL,
  `score_fivestars` int(20) DEFAULT NULL,
  `score_fourstars` int(20) DEFAULT NULL,
  `score_threestars` int(20) DEFAULT NULL,
  `score_twostars` int(20) DEFAULT NULL,
  `score_onestars` int(20) DEFAULT NULL,
  `instalations` int(20) DEFAULT NULL,
  `developeremail` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `developerwebsite` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `physicaladdress` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  `qty_apps` int(11) NOT NULL DEFAULT '0',
  `processed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estrutura da tabela `whois`
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
-- Indexes for table `app_store`
--
ALTER TABLE `app_store`
  ADD PRIMARY KEY (`url`),
  ADD KEY `idx_developername` (`developername`) USING BTREE,
  ADD KEY `idx_developerurl` (`developerurl`) USING BTREE;

--
-- Indexes for table `app_store_developers`
--
ALTER TABLE `app_store_developers`
  ADD PRIMARY KEY (`developername`);

--
-- Indexes for table `ebit`
--
ALTER TABLE `ebit`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `ecommerce`
--
ALTER TABLE `ecommerce`
  ADD PRIMARY KEY (`cnpj`);

--
-- Indexes for table `linkedin`
--
ALTER TABLE `linkedin`
  ADD PRIMARY KEY (`company_name`);

--
-- Indexes for table `play_store`
--
ALTER TABLE `play_store`
  ADD KEY `idx_developer` (`developer`);

--
-- Indexes for table `play_store_developers`
--
ALTER TABLE `play_store_developers`
  ADD PRIMARY KEY (`developer`);

--
-- Indexes for table `whois`
--
ALTER TABLE `whois`
  ADD PRIMARY KEY (`original_domain`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
