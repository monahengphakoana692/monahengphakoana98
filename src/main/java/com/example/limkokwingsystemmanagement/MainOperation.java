package com.example.limkokwingsystemmanagement;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.Collection;

public class MainOperation extends Application
{

    @Override
    public void start(Stage stage) throws IOException
    {
        try {

            MainOperation.pageLoader(stage, "StartHomepage.fxml", "Login",1660,800);
        }catch (Exception e)
        {
            System.out.println("what");
            System.out.println(e);
        }
    }

    public static Collection<? extends Node> pageLoader(Stage stage, String fxml, String title,double width, double height) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainOperation.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Limkokwing Management System -" + title);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        return null;
    }

    public static void main(String[] args)
    {

        launch();


    }


}