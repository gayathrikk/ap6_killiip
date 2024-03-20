package com.test.Database_Testing;

import java.sql.*;

import org.junit.Test;

public class colab_HD_currentdate_login {
	 Connection connection = null;
	
    @Test
    public void run() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
            "jdbc:mysql://private.colab.humanbrain.in:3306/HBA_V2",
            "root", "Health#123");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                "SELECT activity.user,CC_User.user_name,activity.timestamp FROM HBA_V2.activity inner join HBA_V2.CC_User on activity.user=CC_User.id where action=\"High Resolution\" and timestamp>=curdate()*1000000");
            String user;
            int userid;
            String time;
            String format = "%-7d%-30s%-10s%s%n";
            String format2 = "%-7s%-30s%-10s%s%n";
            System.out.printf(format2,"userid","User","action","Time");
            while (resultSet.next()) {
                userid = resultSet.getInt("user");
                user = resultSet.getString("user_name").trim();
                time = resultSet.getString("timestamp").trim();
                System.out.printf(format, userid, user, "login", time);
            }
            resultSet.close();
            statement.close();
            connection.close();
    }


}
