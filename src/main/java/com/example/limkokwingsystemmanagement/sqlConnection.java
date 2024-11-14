package com.example.limkokwingsystemmanagement;

import java.sql.*;
import java.util.Optional;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.mindrot.jbcrypt.BCrypt;


public class sqlConnection {

    // Method to check if the user exists
    private static boolean userExists(Connection connection, String username) throws SQLException
    {
        String query = "SELECT COUNT(*) FROM login WHERE User_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        statement.close();
        return count > 0;
    }

    public static  ResultSet readDatbase(String Query)
    {

        ResultSet resultSet = null;

        try
        {
            PreparedStatement prepareCreateTable = sqlConnection.connecting().prepareStatement(Query);

            resultSet = prepareCreateTable.executeQuery();

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }


        return resultSet;
    }

    public static boolean createTable(String Query, boolean tableCreated)
    {


        try
        {
            Connection connection = sqlConnection.connecting();
            Statement writeStatement = connection.createStatement();
            writeStatement.executeUpdate(Query);
            tableCreated = true;


        }catch (SQLException e)
        {

        }finally
        {
            return tableCreated;
        }


    }
    // Method to hash the password
    public static String hashPassword(String plainPassword)
    {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static void sqlConnect() {



        try
             {
                 Connection connection = sqlConnection.connecting();
                 Statement stmt = connection.createStatement();
                 String sql = "CREATE TABLE academicyears " +
                         "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "year VARCHAR(20))";


            stmt.executeUpdate(sql);

            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static Connection connecting() throws  SQLException
    {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/limkokwingsystem", // Updated with hostname and port
                "root", // Your MySQL username
                "59908114" // Your MySQL password
        );



        return connection;
    }

    public static void closeSqlConnection(Connection connection)
    {
        try
        {
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    public static int insertData(String Query,int verify)
    {
        verify = 0;

        try {
            Connection connection = sqlConnection.connecting();
            Statement sqlStatement = connection.createStatement();

            int rowsAffected = sqlStatement.executeUpdate(Query);

            if (rowsAffected > 0) {
                verify = 1;
            }

        } catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("alert!");
            System.out.println(e);
            alert.setContentText(e.toString());
            Optional<ButtonType> result = alert.showAndWait();
           if( result.get() == ButtonType.CANCEL)
           {
               throw new RuntimeException(e);
           }




        }

        return verify;
    }
}