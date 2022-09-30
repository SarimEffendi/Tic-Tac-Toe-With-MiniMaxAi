package project.Helpers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.JDBC;

public class Database {

    private static Statement statement = null; // For ease of use
    private static Connection connection;

    public static void connect() {
        File dbFile = new File("DB.sqlite");
        boolean dbCreated = dbFile.exists();
        System.out.println("dbFile exists: " + dbCreated);
        try {
            java.sql.DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            statement = connection.createStatement();
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("Error connecting to database");
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Executes a query and returns the result set
     * @param query
     * @return ResultSet
     * @throws SQLException
     */
    public static ResultSet getResult(String query) throws SQLException {
        return Database.statement.executeQuery(query);
    }

    /**
     * Returns row count for Update Queries
     * or 0
     * @param query
     * @return rowcount or 0
     * @throws SQLException
     */
    public static int executeIUD(String query) throws SQLException {
        return Database.statement.executeUpdate(query);
    }

}