package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginController {
    DatabaseConnection2 connectNow = new DatabaseConnection2();
    Connection connectDB = connectNow.getConnection();

    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField passwordtxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private Pane loginPane;

    @FXML
    private Label loginMessagelabel;


    public void LoginButtonClick()
    {
        if(usernametxt.getText().isEmpty()&&passwordtxt.getText().isEmpty())

        {
            loginMessagelabel.setText("Please enter username and password.");
        }
        else

        {
            validateLogin();
        }
        clearfields();
    }

    public void validateLogin()
    {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        String verifyLogin = "SELECT Name, Role, password  FROM roles WHERE TRIM(Name) = ? AND TRIM(password) = ?";

        try {
            PreparedStatement stmt = connectDB.prepareStatement(verifyLogin);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                String roleName = rs.getString("Role");

                redirectUser(roleName);

                logLoginDetails(usernametxt.getText(), roleName);


            } else {
                loginMessagelabel.setText("Invalid Username/password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void clearfields()
    {
        usernametxt.setText("");
        passwordtxt.setText("");
    }

    private void logLoginDetails(String username,String role) throws IOException {

        FileWriter myfile =  new FileWriter("login_log.txt", true);
        BufferedWriter writer = new BufferedWriter(myfile);
        try
        {

            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Write to file
            writer.write("Login at " + currentTime + " - User: " + username + " - Role: " + role);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }
    private void redirectUser(String roleName) throws IOException
    {
        String fxmlFile;

        switch (roleName) {
            case "Faculty Admin":
                fxmlFile = "Admin.fxml";
                try
                {
                    Stage stage  = (Stage) loginPane.getScene().getWindow();
                    stage.close();
                    stage = new Stage();//creating new stage
                    stage.setMaximized(true);
                    /*FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlFile));
                    Pane form = load.load();
                    AdminController controller = load.getController();
                    controller.setUserName(usernametxt.getText());
                    Scene scene =  new Scene(form);
                    stage.setScene(scene);
                    stage.show();*/
                    MainOperation.pageLoader(stage,fxmlFile,"Admin",1900,800);

                } catch (IOException e)
                {
                    Alert warn = new Alert(Alert.AlertType.CONFIRMATION);
                    warn.setContentText(e.toString());
                    warn.show();
                    throw new RuntimeException(e);
                }
                break;
            case "Lecturer":
                fxmlFile = "Lecturer.fxml";
                try
                {
                    Stage stage  = (Stage) loginPane.getScene().getWindow();
                    stage.close();
                    stage.setMaximized(true);
                    stage = new Stage();//creating new stage
                    FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlFile));
                    Pane form = load.load();
                    LecturerController controller = load.getController();
                    controller.setUserName(usernametxt.getText());
                    Scene scene =  new Scene(form);
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e)
                {
                    Alert warn = new Alert(Alert.AlertType.CONFIRMATION);
                    warn.setContentText(e.toString());
                    warn.show();
                    throw new RuntimeException(e);
                }
                break;
            case "Principal Lecturer":
                fxmlFile = "PrincipalLecturer.fxml";
                try
                {
                    Stage stage  = (Stage) loginPane.getScene().getWindow();
                    stage.close();
                    stage = new Stage();//creating new stage
                    FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlFile));
                    Pane form = load.load();
                    PrincipalLecturerController controller = load.getController();
                    controller.setUserName(usernametxt.getText());
                    Scene scene =  new Scene(form);
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e)
                {
                    Alert warn = new Alert(Alert.AlertType.CONFIRMATION);
                    warn.setContentText("hey mr ");
                    warn.show();
                    throw new RuntimeException(e);
                }
                break;
            default:
                loginMessagelabel.setText("Role not recognized. Access denied.");
                return;
        }

    }



}
