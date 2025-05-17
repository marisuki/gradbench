package org.benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.iotdb.jdbc.IoTDBSQLException;

public class IoTDB {
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

        FileWriter fw = new FileWriter("./result/iotdb.txt");
        long t1 = RangeQuery(statement);
        fw.write(t1 + "\n");
        System.out.println(t1);
        long t2 = ValRangeQuery(statement);
        fw.write(t2 + "\n");
        System.out.println(t2);
        long t3 = joinQuery(statement);
        fw.write(t3 + "\n");
        System.out.println(t3);
        long t4 = aggQuery(statement);
        fw.write(t4 + "\n");
        System.out.println(t4);
        fw.close();
    }

    public static long RangeQuery(Statement stmt) throws SQLException {
        long time = System.currentTimeMillis();
        int round = ROUND;
        while(round!=0) {
            for (String path : ts) {
                for (String s : sensor) {
                    String sql = String.format("select %s from %s where Time > %s and Time < %s+%s", s, db + "." + path, minT, minT, duration / 2);
                    stmt.execute(sql);
                }
            }
            round--;
        }
        return System.currentTimeMillis() - time;
    }

    public static long ValRangeQuery(Statement stmt) throws SQLException {
        long time = System.currentTimeMillis();
        int round = ROUND;
        while(round!=0) {
            for (String path : ts) {
                for (String s : sensor) {
                    String sql = String.format("select %s from %s where %s > 0", s, db + "." + path, s);
                    stmt.execute(sql);
                }
            }
            round--;
        }
        return System.currentTimeMillis() - time;
    }

    public static long joinQuery(Statement stmt) throws SQLException {
        long time = System.currentTimeMillis();
        int round = ROUND;
        while(round!=0) {
            String path1 = ts[0];
            String path2 = ts[1];
            for(String s: sensor) {
                String sql = String.format("select %s,%s from %s where Time > %s",path1 + "."+ s, path2 + "."+ s,db,minT);
                stmt.execute(sql);
            }
            round--;
        }
        return System.currentTimeMillis() - time;
    }

    public static long aggQuery(Statement stmt) throws SQLException {
        long time = System.currentTimeMillis();
        int round = ROUND;
        while(round!=0) {
            for (String path : ts) {
                for (String s : sensor) {
                    String sql = String.format("select avg(%s) from %s where Time > %s and Time < %s+%s", path + "." + s, db, minT, minT, duration/2);
                    stmt.execute(sql);
                }
            }
            round--;
        }
        return System.currentTimeMillis() - time;
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