CREATE DATABASE CLUB;
USE CLUB;

CREATE TABLE `guest` (
                         `ID` bigint(20) NOT NULL,
                         `USERID` bigint(20) NOT NULL,
                         `PARTNERID` bigint(20) NOT NULL,
                         `STATUS` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `invoice` (
                           `ID` bigint(20) NOT NULL,
                           `PERSONID` bigint(20) NOT NULL,
                           `PARTNERID` bigint(20) NOT NULL,
                           `CREATIONDATE` datetime NOT NULL,
                           `AMOUNT` double NOT NULL,
                           `STATUS` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `invoicedetail` (
                                 `ID` bigint(20) NOT NULL,
                                 `INVOICEID` bigint(20) NOT NULL,
                                 `ITEM` int(11) NOT NULL,
                                 `DESCRIPTION` varchar(100) NOT NULL,
                                 `AMOUNT` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `partner` (
                           `ID` bigint(20) NOT NULL,
                           `USERID` bigint(20) NOT NULL,
                           `AMOUNT` double NOT NULL,
                           `TYPE` varchar(50) NOT NULL,
                           `CREATIONDATE` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `person` (
                          `ID` bigint(20) NOT NULL,
                          `DOCUMENT` bigint(20) NOT NULL,
                          `NAME` varchar(100) NOT NULL,
                          `CELLPHONE` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `user` (
                        `ID` bigint(20) NOT NULL,
                        `PERSONID` bigint(20) NOT NULL,
                        `USERNAME` varchar(50) NOT NULL,
                        `PASSWORD` varchar(50) NOT NULL,
                        `ROLE` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `guest`
    ADD PRIMARY KEY (`ID`),
  ADD KEY `PERTNERID` (`PARTNERID`),
  ADD KEY `USERID` (`USERID`);

ALTER TABLE `invoice`
    ADD PRIMARY KEY (`ID`),
  ADD KEY `PARTNERID` (`PARTNERID`),
  ADD KEY `PERSONID` (`PERSONID`);

ALTER TABLE `invoicedetail`
    ADD PRIMARY KEY (`ID`),
  ADD KEY `INVOICEID` (`INVOICEID`);

ALTER TABLE `partner`
    ADD PRIMARY KEY (`ID`),
  ADD KEY `USERID` (`USERID`);

ALTER TABLE `person`
    ADD PRIMARY KEY (`ID`);

ALTER TABLE `user`
    ADD PRIMARY KEY (`ID`),
  ADD KEY `PERSONID` (`PERSONID`);

ALTER TABLE `guest`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `invoice`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `invoicedetail`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `partner`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `person`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `user`
    MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `guest`
    ADD CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`PARTNERID`) REFERENCES `partner` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `guest_ibfk_2` FOREIGN KEY (`USERID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `invoice`
    ADD CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`PARTNERID`) REFERENCES `partner` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invoice_ibfk_2` FOREIGN KEY (`PERSONID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `invoicedetail`
    ADD CONSTRAINT `invoicedetail_ibfk_1` FOREIGN KEY (`INVOICEID`) REFERENCES `invoice` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `partner`
    ADD CONSTRAINT `partner_ibfk_1` FOREIGN KEY (`USERID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user`
    ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`PERSONID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;
