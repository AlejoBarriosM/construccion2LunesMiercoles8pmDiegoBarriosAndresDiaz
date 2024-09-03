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
    ADD KEY `PARTNERID` (`PARTNERID`),
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

ALTER TABLE `partner` CHANGE `CREATIONDATE` `CREATIONDATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `invoice` CHANGE `CREATIONDATE` `CREATIONDATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

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

INSERT INTO `person` (`ID`, `DOCUMENT`, `NAME`, `CELLPHONE`) VALUES (NULL, '1234', 'admin', '123456789');
INSERT INTO `user` (`ID`, `PERSONID`, `USERNAME`, `PASSWORD`, `ROLE`) VALUES (NULL, '1', 'admin', 'admin', 'administrador');

CREATE
    VIEW `vw_viprequests` AS
SELECT
    `pt`.`ID` AS `ID`,
    `pt`.`USERID` AS `USERID`,
    `pt`.`AMOUNT` AS `AMOUNT`,
    SUM(`iv`.`AMOUNT`) AS `AMOUNT_INVOICE`,
    `pt`.`TYPE` AS `TYPE`,
    `pt`.`CREATIONDATE` AS `CREATIONDATE`
FROM
    (`partner` `pt`
        LEFT JOIN `invoice` `iv` ON (`pt`.`ID` = `iv`.`PARTNERID`
        AND `iv`.`STATUS` = 'Pagó'))
WHERE
    pt.TYPE = 'Pendiente'
GROUP BY pt.ID , pt.USERID , pt.AMOUNT , pt.TYPE , pt.CREATIONDATE
ORDER BY SUM(iv.AMOUNT) DESC , pt.AMOUNT DESC , pt.CREATIONDATE
LIMIT 5;

COMMIT;

