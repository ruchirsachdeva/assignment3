-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 04, 2018 at 10:43 AM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `pd_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE `medicine` (
  `medicine_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`medicine_id`, `name`) VALUES
  (1, 'Medicine 1'),
  (2, 'Medicine 2');

-- --------------------------------------------------------

--
-- Table structure for table `note`
--

CREATE TABLE `note` (
  `note_id` int(11) NOT NULL,
  `test_session_id` int(11) NOT NULL,
  `note` longtext NOT NULL,
  `med_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `note`
--

INSERT INTO `note` (`note_id`, `test_session_id`, `note`, `med_id`) VALUES
  (1, 1, 'Well this is interesting.', 2),
  (2, 1, 'This seams a bit weird.', 1);

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `organization_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`organization_id`, `name`) VALUES
  (1, 'Hospital'),
  (2, 'LNU University');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `name`, `type`) VALUES
  (1, 'patient', '1'),
  (2, 'physician', '2'),
  (3, 'researcher', '3'),
  (4, 'junior researcher', '3');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `test_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `therapy_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`test_id`, `date_time`, `therapy_id`) VALUES
  (1, '2009-12-01 18:00:00', 1),
  (2, '2009-12-02 18:00:00', 1),
  (3, '2009-12-02 18:00:00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `test_session`
--

CREATE TABLE `test_session` (
  `test_session_id` int(11) NOT NULL,
  `test_type` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `data_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test_session`
--

INSERT INTO `test_session` (`test_session_id`, `test_type`, `test_id`, `data_url`) VALUES
  (1, 1, 1, 'data1'),
  (2, 2, 1, 'data2'),
  (3, 1, 2, 'data3'),
  (4, 2, 2, 'data4'),
  (5, 1, 3, 'data5'),
  (6, 2, 3, 'data6');

-- --------------------------------------------------------

--
-- Table structure for table `therapy`
--

CREATE TABLE `therapy` (
  `therapy_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `therapylist_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `therapy`
--

INSERT INTO `therapy` (`therapy_id`, `patient_id`, `med_id`, `therapylist_id`) VALUES
  (1, 3, 1, 1),
  (2, 4, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `therapy_list`
--

CREATE TABLE `therapy_list` (
  `therapy_list_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `medicine_id` int(11) NOT NULL,
  `dosage` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `therapy_list`
--

INSERT INTO `therapy_list` (`therapy_list_id`, `name`, `medicine_id`, `dosage`) VALUES
  (1, 'Therapy trials with Medicine 1', 1, '400 ml');

-- --------------------------------------------------------

--
-- Table structure for table `Car`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `organization_id` int(11) DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `long` float DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_confirm` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Car`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `role_id`, `organization_id`, `lat`, `long`) VALUES
  (1, 'doc', 'doc@hospital.com', 2, 1, NULL, NULL),
  (2, 'researcher', 'res@uni.se', 3, 2, NULL, NULL),
  (3, 'patient1', 'x@gmail.com', 1, 1, 59.6567, 16.6709),
  (4, 'patient2', 'y@happyemail.com', 1, 1, 57.3365, 12.5164);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`medicine_id`);

--
-- Indexes for table `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `fk_Test_SessionID_idx` (`test_session_id`),
  ADD KEY `fk_UserID_idx` (`med_id`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`organization_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`test_id`),
  ADD KEY `fk_TherapyID_idx` (`therapy_id`);

--
-- Indexes for table `test_session`
--
ALTER TABLE `test_session`
  ADD PRIMARY KEY (`test_session_id`),
  ADD KEY `fk_TestID_idx` (`test_id`);

--
-- Indexes for table `therapy`
--
ALTER TABLE `therapy`
  ADD PRIMARY KEY (`therapy_id`),
  ADD KEY `fk_UserID_Patient_idx` (`patient_id`),
  ADD KEY `fk_UserID_medic_idx` (`med_id`),
  ADD KEY `fk_Therapy_ListID_idx` (`therapylist_id`);

--
-- Indexes for table `therapy_list`
--
ALTER TABLE `therapy_list`
  ADD PRIMARY KEY (`therapy_list_id`),
  ADD KEY `fk_medicineID_idx` (`medicine_id`);

--
-- Indexes for table `Car`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD KEY `roleID_idx` (`role_id`),
  ADD KEY `fk_User_Organization_idx` (`organization_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `medicine`
--
ALTER TABLE `medicine`
  MODIFY `medicine_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `note`
--
ALTER TABLE `note`
  MODIFY `note_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `organization_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `test_session`
--
ALTER TABLE `test_session`
  MODIFY `test_session_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `therapy`
--
ALTER TABLE `therapy`
  MODIFY `therapy_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `therapy_list`
--
ALTER TABLE `therapy_list`
  MODIFY `therapy_list_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Car`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `fk_Test_SessionID` FOREIGN KEY (`test_session_id`) REFERENCES `test_session` (`test_session_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID` FOREIGN KEY (`med_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `fk_TherapyID` FOREIGN KEY (`therapy_id`) REFERENCES `therapy` (`therapy_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test_session`
--
ALTER TABLE `test_session`
  ADD CONSTRAINT `fk_TestID` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `therapy`
--
ALTER TABLE `therapy`
  ADD CONSTRAINT `fk_Therapy_ListID` FOREIGN KEY (`therapylist_id`) REFERENCES `therapy_list` (`therapy_list_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_Patient` FOREIGN KEY (`patient_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_medic` FOREIGN KEY (`med_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `therapy_list`
--
ALTER TABLE `therapy_list`
  ADD CONSTRAINT `fk_MedicineID` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`medicine_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_User_Organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_User_Role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;