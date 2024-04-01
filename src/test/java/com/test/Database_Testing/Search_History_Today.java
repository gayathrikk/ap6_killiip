package com.test.Database_Testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.testng.annotations.Test;

public class Search_History_Today {
	
	@Test
	public void testDB() throws ClassNotFoundException, SQLException {
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded");

	    String url = "jdbc:mysql://apollo2.humanbrain.in:3306/HBA_V2";
	    String username = "root";
	    String password = "Health#123";
	    Connection connection = DriverManager.getConnection(url, username, password);
	    System.out.println("MYSQL database connect");
	    
	    executeAndPrintQuery(connection);
	    connection.close();
	}

	private void executeAndPrintQuery(Connection connection) throws SQLException {
	    Statement statement = connection.createStatement();
	    String query1 = "SELECT a.id,b.user_name,a.person,a.query,a.search_ts\r\n"
	    		+ "FROM tag_search_history as a\r\n"
	    		+ "INNER JOIN CC_User as b ON (a.person=b.id)\r\n"
	    		+ "WHERE DATE(a.search_ts) = CURRENT_DATE;";

	    ResultSet resultSet = statement.executeQuery(query1);
	    
	    int IdWidth = 10;
	    int user_nameWidth = 30;
	    int personWidth = 15;
	    int queryWidth = 50;
	    int search_tsWidth = 25;

	    // Printing header
	    System.out.printf("%-" + IdWidth + "s %-"+ user_nameWidth + "s %-"+ personWidth + "s %-"+ queryWidth + "s %-" + search_tsWidth + "s%n",
	            "Id", "User_name", "person", "query", "search_ts");

	    // Printing separator line
	    String separatorLine = "-".repeat(IdWidth + user_nameWidth + personWidth + queryWidth + search_tsWidth);
	    System.out.println(separatorLine);
	    
	    while (resultSet.next()) {
	        Integer id = resultSet.getInt("id");
	        String user_name = resultSet.getString("user_name");
	        String person = resultSet.getString("person");
	        String query = resultSet.getString("query");
	        Timestamp search_ts = resultSet.getTimestamp("search_ts");
	        
	        System.out.printf("%-" + IdWidth + "d %-" + user_nameWidth + "s %-" + personWidth + "s %-" + queryWidth + "s %-" + search_tsWidth + "s%n",
	               id, user_name, person, query, search_ts);
	    }

	    // Close the statement
	    resultSet.close();
	    statement.close();
	}

}
