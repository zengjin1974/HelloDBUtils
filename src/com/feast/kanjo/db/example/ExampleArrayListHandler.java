/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */
package com.feast.kanjo.db.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

public class ExampleArrayListHandler implements MySqlIF {

    public static void main(String[] args) throws SQLException {

        QueryRunner run = new QueryRunner();

        DbUtils.loadDriver(driver);
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        // -----------------------------------------------------------------------------------

        try {
            List<Object[]> query = run.query(conn, "SELECT * FROM employee", new ArrayListHandler());
            for (Object[] objects : query) {
                System.out.println(Arrays.toString(objects));
            }
        } finally {
            DbUtils.close(conn);
        }

    }
}
