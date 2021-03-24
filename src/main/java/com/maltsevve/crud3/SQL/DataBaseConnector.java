package com.maltsevve.crud3.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/mysqldemo";
    private final static String USER = "mysql";
    private final static String PASSWORD = "q1w2e3r4";

    private static DataBaseConnector dataBaseConnector;
    private static Connection connection;

    public static synchronized DataBaseConnector getDataBaseConnector() {
        if (dataBaseConnector == null) {
            dataBaseConnector = new DataBaseConnector();
        }
        return dataBaseConnector;
    }

    private DataBaseConnector() {

    }

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
