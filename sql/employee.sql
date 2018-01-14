DROP TABLE `employee`;

CREATE TABLE `employee` (
  `EMPLOYEEID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMPLOYEENAME` varchar(255) DEFAULT NULL,
  `EMPLOYEE_ADDRESS	` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEEID`)
);

insert into `employee` (`EMPLOYEEID`, `EMPLOYEENAME`, `EMPLOYEE_ADDRESS`) values('1','Rockey','1');
insert into `employee` (`EMPLOYEEID`, `EMPLOYEENAME`, `EMPLOYEE_ADDRESS`) values('2','Jose','2');