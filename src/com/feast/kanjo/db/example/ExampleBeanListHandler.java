/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */

package com.feast.kanjo.db.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class ExampleBeanListHandler implements PostgreIF {

    public static void main(String[] args) throws SQLException {

        QueryRunner run = new QueryRunner();

        DbUtils.loadDriver(driver);
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        // -----------------------------------------------------------------------------------
        ResultSetHandler<List<Employee>> resultListHandler = new BeanListHandler<Employee>(Employee.class);

        try {
            List<Employee> empList = run.query(conn, "SELECT * FROM employee", resultListHandler);

            System.out.println(empList);

    		for (Employee emp : empList) {
                System.out.println(emp.getEmployee_id());
                System.out.println(emp.getEmployee_name());
                System.out.println(emp.getEmployee_address());
                System.out.println(emp.getEmployee_salary());
                System.out.println(emp.getEmployee_birth());
    		}

        } finally {
            DbUtils.close(conn);
        }

    }
}