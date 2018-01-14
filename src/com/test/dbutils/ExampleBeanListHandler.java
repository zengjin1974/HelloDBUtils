/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */

package com.test.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class ExampleBeanListHandler {

    public static void main(String[] args) throws SQLException {

        final String url = "jdbc:mysql://10.167.39.187:3306/lab";
        final String driver = "com.mysql.jdbc.Driver";
        final String usr = "root";
        final String pwd = "admin2017";

        QueryRunner run = new QueryRunner();

        DbUtils.loadDriver(driver);
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        // -----------------------------------------------------------------------------------
        ResultSetHandler<List<Employee>> resultListHandler = new BeanListHandler<Employee>(Employee.class);

        try {
            List<Employee> empList = run.query(conn, "SELECT * FROM employee", resultListHandler);

            System.out.println(empList);

    		for (Employee bean : empList) {

    			System.out.println(bean.getEmployeeId());
    			System.out.println(bean.getEmployeename());
       			System.out.println(bean.getEmployee_address());
    		}

        } finally {
            DbUtils.close(conn);
        }

    }
}