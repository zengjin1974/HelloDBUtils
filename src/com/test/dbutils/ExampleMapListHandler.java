/*
 * https://www.javatips.net/blog/apache-dbutils-tutorial
 */

package com.test.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class ExampleMapListHandler implements PostgreIF {

   public static void main(String[] args) throws SQLException {

       QueryRunner run = new QueryRunner();

       DbUtils.loadDriver(driver);
       Connection conn = DriverManager.getConnection(url, usr, pwd);
       // -----------------------------------------------------------------------------------

       try {
           List<Map<String, Object>> maps = run.query(conn, "SELECT * FROM employee",
        		   new MapListHandler());

           System.out.println(maps);


       } finally {
           DbUtils.close(conn);
       }

   }
}