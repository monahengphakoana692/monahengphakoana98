package com.example.limkokwingsystemmanagement;

import javafx.scene.control.Alert;

import java.sql.*;

public class JDBC
{
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/PrincipalLectures";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "999999";

    public static Connection connecting()
    {
        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return connection;
    }
    // Method to insert a new user into the database
    public static boolean insertUser(String username, String password, String role) {

        String insertQuery = "INSERT INTO users (Username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("User inserted successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;  // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int Insertion(String query) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);

            return 1;
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText(e.toString());
            error.showAndWait();

            e.printStackTrace();
        }
        return 0;

    }

    // Method to validate user login
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE Username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();  // Returns true if a matching record is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method to retrieve data from database
    public static ResultSet retrieveUser(String query)
    {
        ResultSet colle = null;

        try {
            Connection connection = JDBC.connecting();

            PreparedStatement collect = connection.prepareStatement(query);
            colle = collect.executeQuery();


        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText(e.toString());
            error.showAndWait();


        }

        return colle;


    }

    public static void closeSql()
    {
        try
        {
            JDBC.connecting().close();

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }
}