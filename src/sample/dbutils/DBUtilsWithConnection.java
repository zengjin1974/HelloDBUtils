/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */

package sample.dbutils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class DBUtilsWithConnection {

    public static void main(String[] args) throws SQLException {

        final String url = "jdbc:mysql://10.167.39.187:3306/lab";
        final String driver = "com.mysql.jdbc.Driver";
        final String usr = "root";
        final String pwd = "admin2017";

        QueryRunner run = new QueryRunner();

        DbUtils.loadDriver(driver);
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        // -----------------------------------------------------------------------------------
        ResultSetHandler<Employee> resultHandler = new BeanHandler<Employee>(Employee.class);


        try {
            Employee emp = run.query(conn, "SELECT * FROM employee WHERE employeename=?",
                    resultHandler, "Jose");
            System.out.println(emp.getEmployeeId());
        } finally {
            DbUtils.close(conn);
        }

    }
}

