/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.pm.report.generator.db;

import java.sql.*;
import java.util.List;
import pl.pm.report.generator.model.Store;


public class PropertiesTable {
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:mdb";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    Connection conn = null;
    Statement stmt = null;


    public void startConnection() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void display() {
        try {
            stmt = conn.createStatement();
            String sql = "select * from STORE";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getInt(3));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public PropertiesTable() {
    }

    public void createDB() {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Store " +
                    "(id INT AUTO_INCREMENT NOT NULL, " +
                    " operation VARCHAR(255) NOT NULL," +
                    " amount INT NOT NULL)";
            stmt.executeUpdate(sql);
            System.out.println("Table Created");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void insertStoreToDB(List<Store> stores) {
        try {
            stmt = conn.createStatement();
            for (Store store : stores) {
                System.out.println(store.getOperationType() + "," + store.getAmount());
                String sql = "INSERT INTO Store (operation, amount) Values ('" + store.getOperationType() + "', " + store.getAmount() + ");";
                stmt.executeUpdate(sql);

            }
            System.out.println("Insert ok");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int selectSumOfOperationBuy() throws SQLException {
        int buySum = 0;
        stmt = conn.createStatement();
        String sql = "SELECT SUM(amount) FROM store WHERE operation='buy'";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            buySum = resultSet.getInt(1);
        }
        return buySum;
    }

    public int selectSumOfOperationSupply() throws SQLException {
        int supplySum = 0;
        stmt = conn.createStatement();
        String sql = "SELECT SUM(amount) FROM store WHERE operation='supply'";
        try (ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                supplySum = resultSet.getInt(1);
            }
        }
        return supplySum;
    }

    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }

        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
    }
}
