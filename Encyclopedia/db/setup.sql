-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 13, 2023 at 03:55 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `january`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `bookID` int(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `date` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `poem`
--

CREATE TABLE `poem` (
  `Poem_ID` int(11) NOT NULL,
  `Book_ID` int(11) NOT NULL,
  `Poem_Title` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roots`
--

CREATE TABLE `roots` (
  `root_id` int(11) NOT NULL,
  `root_name` varchar(10) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `tokenID` int(11) NOT NULL,
  `tokenName` varchar(255) NOT NULL,
  `POS` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `verse`
--

CREATE TABLE `verse` (
  `Verse_ID` int(11) NOT NULL,
  `Poem_ID` int(11) NOT NULL,
  `Misra_1` text NOT NULL,
  `Misra_2` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `verse_root`
--

CREATE TABLE `verse_root` (
  `id` int(11) NOT NULL,
  `verse_id` int(11) NOT NULL,
  `root_id` int(11) NOT NULL,
  `verification_status` varchar(30) CHARACTER SET utf16 NOT NULL DEFAULT 'Auto'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `verse_token`
--

CREATE TABLE `verse_token` (
  `id` int(11) NOT NULL,
  `root_id` int(11) NOT NULL,
  `token_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `poem`
--
ALTER TABLE `poem`
  ADD PRIMARY KEY (`Poem_ID`),
  ADD KEY `poem_ibfk_1` (`Book_ID`);

--
-- Indexes for table `roots`
--
ALTER TABLE `roots`
  ADD PRIMARY KEY (`root_id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`tokenID`),
  ADD UNIQUE KEY `tokenName` (`tokenName`);

--
-- Indexes for table `verse`
--
ALTER TABLE `verse`
  ADD PRIMARY KEY (`Verse_ID`),
  ADD KEY `fk_poem` (`Poem_ID`);

--
-- Indexes for table `verse_root`
--
ALTER TABLE `verse_root`
  ADD PRIMARY KEY (`id`),
  ADD KEY `verse_root_ibfk_1` (`verse_id`),
  ADD KEY `verse_root_ibfk_2` (`root_id`);

--
-- Indexes for table `verse_token`
--
ALTER TABLE `verse_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `verse_token_ibfk_1` (`root_id`),
  ADD KEY `verse_token_ibfk_2` (`token_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `bookID` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `poem`
--
ALTER TABLE `poem`
  MODIFY `Poem_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roots`
--
ALTER TABLE `roots`
  MODIFY `root_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `tokenID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `verse`
--
ALTER TABLE `verse`
  MODIFY `Verse_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `verse_root`
--
ALTER TABLE `verse_root`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `verse_token`
--
ALTER TABLE `verse_token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `poem`
--
ALTER TABLE `poem`
  ADD CONSTRAINT `poem_ibfk_1` FOREIGN KEY (`Book_ID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `verse`
--
ALTER TABLE `verse`
  ADD CONSTRAINT `fk_poem` FOREIGN KEY (`Poem_ID`) REFERENCES `poem` (`Poem_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `verse_root`
--
ALTER TABLE `verse_root`
  ADD CONSTRAINT `verse_root_ibfk_1` FOREIGN KEY (`verse_id`) REFERENCES `verse` (`Verse_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `verse_root_ibfk_2` FOREIGN KEY (`root_id`) REFERENCES `roots` (`root_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `verse_token`
--
ALTER TABLE `verse_token`
  ADD CONSTRAINT `verse_token_ibfk_1` FOREIGN KEY (`root_id`) REFERENCES `roots` (`root_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `verse_token_ibfk_2` FOREIGN KEY (`token_id`) REFERENCES `token` (`tokenID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
