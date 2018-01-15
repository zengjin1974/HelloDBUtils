DROP TABLE `employee`;

CREATE TABLE `employee` (
  `EMPLOYEE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_NAME` varchar(255) DEFAULT NULL,
  `EMPLOYEE_ADDRESS` varchar(255) DEFAULT NULL,
  `EMPLOYEE_BIRTH` datetime DEFAULT NULL,
  `EMPLOYEE_SALARY` decimal(5) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`)
);

insert into `employee` values('1','Rockey','Beijing', STR_TO_DATE('19740206', '%Y%m%d'), 2000);
insert into `employee` values('2','Jose','Shanghai', STR_TO_DATE('19800101', '%Y%m%d'), 5000);