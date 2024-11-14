package com.example.limkokwingsystemmanagement;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection2
{
    public Connection databaseLink;

    public Connection getConnection()
    {
        String databseName = "limkokwingsystem";
        String databaseUser = "root";
        String databsePassword = "59908114";
        String url = "jdbc:mysql://localhost/" + databseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databsePassword);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return databaseLink;
    }
}