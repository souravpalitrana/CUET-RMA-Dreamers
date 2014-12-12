-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2014 at 08:01 AM
-- Server version: 5.1.37
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `imedikit`
--

-- --------------------------------------------------------

--
-- Table structure for table `district`
--

CREATE TABLE IF NOT EXISTS `district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(100) NOT NULL,
  `district_code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `district`
--

INSERT INTO `district` (`id`, `district_name`, `district_code`) VALUES
(1, 'Chittagong', 'dis001'),
(2, 'Dhaka', 'dis002');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_info`
--

CREATE TABLE IF NOT EXISTS `doctor_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `qualification` varchar(1000) NOT NULL,
  `chember` varchar(1000) NOT NULL,
  `speciality` varchar(1000) NOT NULL,
  `visit` varchar(100) NOT NULL,
  `working_day` varchar(100) NOT NULL,
  `lat` varchar(100) NOT NULL,
  `lon` varchar(100) NOT NULL,
  `spec_code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `doctor_info`
--

INSERT INTO `doctor_info` (`id`, `name`, `qualification`, `chember`, `speciality`, `visit`, `working_day`, `lat`, `lon`, `spec_code`) VALUES
(1, 'Sujat Pal', 'MBBS from CMC', 'Popular Digonostic Center', 'Mediciine and Surgery ', '500', 'Sat-Thur', '22.9191', '91.5672', 'dis001_spec001'),
(2, 'Palash Biswas', 'MBBS from DMC', 'DMC', 'Neorologist', '1000', 'Sunday-Thursday', '22.9191', '91.5672', 'dis001_spec001');

-- --------------------------------------------------------

--
-- Table structure for table `herbal`
--

CREATE TABLE IF NOT EXISTS `herbal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `herbal`
--

INSERT INTO `herbal` (`id`, `name`, `code`) VALUES
(1, 'Abscess', '00001'),
(2, 'Acid Reflux', '00002');

-- --------------------------------------------------------

--
-- Table structure for table `herbal_description`
--

CREATE TABLE IF NOT EXISTS `herbal_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remedy` varchar(100) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `herbal_description`
--

INSERT INTO `herbal_description` (`id`, `remedy`, `description`, `code`) VALUES
(1, 'Compress', 'Soak a wash cloth or gauze in one cup of hot water and 2 tablespoon of epsom salt', '00001'),
(2, 'Tea tree', 'Apply tea tree oil in diluted form three times a day', '00001'),
(3, 'Aloe vera', 'Take 1/2 cup of aloe vera juicie three times a day between meals. It helps to soothe an irritated esophagus', '00002'),
(4, 'Marshmallow', 'Drink a tea made from 1-3gm of chamomile flowers three or four times a day', '00002');

-- --------------------------------------------------------

--
-- Table structure for table `hot_topic`
--

CREATE TABLE IF NOT EXISTS `hot_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `description` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `hot_topic`
--

INSERT INTO `hot_topic` (`id`, `name`, `description`) VALUES
(1, 'Ebola', 'It is dangerous');

-- --------------------------------------------------------

--
-- Table structure for table `special`
--

CREATE TABLE IF NOT EXISTS `special` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `spec_code` varchar(100) NOT NULL,
  `district_code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `special`
--

INSERT INTO `special` (`id`, `name`, `spec_code`, `district_code`) VALUES
(1, 'Medicine', 'dis001_spec001', 'dis001'),
(2, 'Orthopedix', 'dis001_spec002', 'dis001'),
(3, 'Neurology', 'dis001_spec003', 'dis001'),
(4, 'Cardiology', 'dis001_spec004', 'dis001');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
