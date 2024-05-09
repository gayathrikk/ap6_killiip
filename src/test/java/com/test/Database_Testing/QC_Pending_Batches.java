package com.test.Database_Testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

public class QC_Pending_Batches {
    
    @Test
    public void testDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");

        String url = "jdbc:mysql://apollo2.humanbrain.in:3306/HBA_V2";
        String username = "root";
        String password = "Health#123";
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("MYSQL database connected");
        
        executeAndPrintQuery(connection);
        connection.close();
    }

    private void executeAndPrintQuery(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query1 = "SELECT id,name,datalocation,arrival_date,totalImages FROM `slidebatch` WHERE `process_status` = 6 AND `arrival_date` < DATE_SUB(NOW(), INTERVAL 4 DAY);";

        ResultSet resultSet = statement.executeQuery(query1);
        
        int IdWidth = 10;
        int nameWidth = 40;
        int datalocationWidth = 30;
        int arrival_dateWidth = 20;
        int totalImagesWidth = 25;

        // Printing header
        System.out.printf("%-" + IdWidth + "s %-"+ nameWidth + "s %-"+ datalocationWidth + "s %-"+ arrival_dateWidth + "s %-" + totalImagesWidth + "s%n",
                "Id", "name", "datalocation", "arrival_date", "totalImages");

        // Printing separator line
        String separatorLine = "-".repeat(IdWidth + nameWidth + datalocationWidth + arrival_dateWidth + totalImagesWidth);
        System.out.println(separatorLine);
        
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String datalocation = resultSet.getString("datalocation");
            String arrival_date = resultSet.getString("arrival_date"); // Assuming arrival_date is stored as a String
            int totalImages = resultSet.getInt("totalImages");

            System.out.printf("%-" + IdWidth + "d %-" + nameWidth + "s %-" + datalocationWidth + "s %-" + arrival_dateWidth + "s %-" + totalImagesWidth + "d%n",
                   id, name, datalocation, arrival_date, totalImages);
        }

        // Close the statement
        resultSet.close();
        statement.close();
    }
}



