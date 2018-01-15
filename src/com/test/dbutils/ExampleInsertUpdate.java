/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */
package com.test.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

public class ExampleInsertUpdate implements PostgreIF {

    public static void main(String[] args) throws SQLException {

        QueryRunner run = new QueryRunner();

        DbUtils.loadDriver(driver);
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        // -----------------------------------------------------------------------------------

        try {
            // Execute the SQL update statement and return the number of
            // inserts that were made
            int inserts = run.update(conn, "INSERT INTO employee (employee_id, employee_name) VALUES (?,?)", 10, "Arun");

            // The line before uses varargs and autoboxing to simplify the code
            System.out.println("inserts "+inserts);
            // Now it's time to rise to the occation...
            int updates = run.update(conn, "UPDATE employee SET employee_name=? WHERE employee_id=?", "Simon",1);
            System.out.println("updates "+updates);
        } catch (SQLException sqle) {
            // Handle it
            sqle.printStackTrace();
        }

    }
}