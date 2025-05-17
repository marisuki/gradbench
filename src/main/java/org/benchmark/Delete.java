package org.benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.iotdb.jdbc.IoTDBSQLException;

public class Delete {
    public static String db = "root.sg";
    public static String[] ts = {"track8.d1", "track9.d2"};
    public static String[] sensor = {"voltage","humi1","humi2","radius","srcad1","srcad2","srcad0","alarm"};
    public static String minT = "2023-03-15T11:57:16.427";
    public static long duration = 3647726504L;
    public static int ROUND = 2;

    public static void benchmark() throws SQLException, IOException {
        Connection connection = getConnection();
        if (connection == null) {
            System.out.println("get connection defeat");
            return;
        }
        Statement statement = connection.createStatement();
        //Create database
        try {
            statement.execute("CREATE DATABASE root.demo");
        } catch (IoTDBSQLException e){
            System.out.println(e.getMessage());
        }

        remove(statement);
    }

    public void remove(Statement stmt) throws SQLException {
        stmt.execute("delete database root.**;");
    }

    public static Connection getConnection() {
        // JDBC driver name and database URL
        String driver = "org.apache.iotdb.jdbc.IoTDBDriver";
        String url = "jdbc:iotdb://127.0.0.1:6667/";
        // set rpc compress mode
        // String url = "jdbc:iotdb://127.0.0.1:6667?rpc_compress=true";

        // Database credentials
        String username = "root";
        String password = "root";

        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException, IOException {
        benchmark();
    }
}