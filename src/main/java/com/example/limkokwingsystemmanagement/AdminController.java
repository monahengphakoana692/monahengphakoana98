package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminController implements Initializable
{

    @FXML
    private Text AddSemId;

    @FXML
    private Label LblName;

    @FXML
    private Text AssignId;

    @FXML
    private Text ViewId;

    @FXML
    private Text newLectId;

    @FXML
    private ImageView ProfilePicture;

    @FXML
    private Text newYearId;

    @FXML
    private Pane sidepainid;

    @FXML
    private Pane displayContent;

    private String Academicyear;
    @FXML
    private Rectangle RectangePane;

    @FXML
    private Label currentAcademicyear;

    @FXML
    private ImageView imageBackGround;

    private String userName;

    @FXML
    void NewLecturer(MouseEvent event)
    {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setHeight(20);
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setRadius(40);

       try
        {
            FXMLLoader load = new FXMLLoader(getClass().getResource("addLecturer.fxml"));
            Pane form = load.load();
            double height = displayContent.getHeight();
            double Width = displayContent.getWidth();
            form.setMaxWidth(Width);
            form.setMaxHeight(height);
            displayContent.getChildren().clear();
            displayContent.getChildren().addAll(form);

        } catch (IOException e)
        {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setContentText(e.toString());
            Optional<ButtonType> result = warning.showAndWait();
            throw new RuntimeException(e);
        }

    }


    @FXML
    void AddSemester(MouseEvent event)
    {
        try
        {
            FXMLLoader load = new FXMLLoader(getClass().getResource("addingSemestersNmod.fxml"));
            Pane form = load.load();
            AddingSemestersNmodCotroller controller = load.getController();
            double height = displayContent.getHeight();
            double Width = displayContent.getWidth();
            form.setMaxWidth(Width);
            form.setMaxHeight(height);
            displayContent.getChildren().clear();
            displayContent.getChildren().addAll(form);
            receivingAcYear(controller);

        } catch (IOException e)
        {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setContentText(e.toString());
            Optional<ButtonType> result = warning.showAndWait();
            throw new RuntimeException(e);
        }
    }


    @FXML
    void AssignLects(MouseEvent event)
    {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setHeight(20);
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setRadius(40);
        try
        {
            FXMLLoader load = new FXMLLoader(getClass().getResource("AssignLecturer.fxml"));
            Pane form = load.load();
            form.setEffect(shadow);
            displayContent.getChildren().clear();
            displayContent.getChildren().addAll(form);

        } catch (IOException e)
        {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setContentText(e.toString());
            Optional<ButtonType> result = warning.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ViewLecturers(MouseEvent event)
    {
        try
        {
            FXMLLoader load = new FXMLLoader(getClass().getResource("ViewLecturer.fxml"));
            Pane form = load.load();

            displayContent.getChildren().clear();
            displayContent.getChildren().addAll(form);

        } catch (IOException e)
        {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setContentText(e.toString());
            Optional<ButtonType> result = warning.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addYear(MouseEvent event)
    {

        BoxBlur blur = new BoxBlur(5, 5, 3);
        Parent primaryStage = displayContent.getScene().getRoot();
        primaryStage.getScene().getRoot().setEffect(blur);

        Dialog<String> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Academic Year");

        Label label = new Label("Enter your Academic year:");
        TextField Years = new TextField();
        label.setLabelFor(Years);
        VBox dialogContent = new VBox(label, Years);
        dialog.getDialogPane().setContent(dialogContent);

        ButtonType submitButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, cancel);

        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton == submitButton)
            {
                try
                {
                    FXMLLoader load = new FXMLLoader(getClass().getResource("addingSemestersNmod.fxml"));
                    Pane form = load.load();
                    AddingSemestersNmodCotroller controller = load.getController();
                    form.setLayoutX(-260);
                    form.setLayoutY(-30);
                    double height = displayContent.getHeight();
                    double Width = displayContent.getWidth();
                    form.setMaxWidth(Width);
                    form.setMaxHeight(height);
                    displayContent.getChildren().clear();
                    displayContent.getChildren().addAll(form);
                    YearToStorage(Years.getText());//insertting into table
                    yearToprofile();//setting a year to profile
                    receivingAcYear(controller);


                } catch (IOException e)
                {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setContentText(e.toString());
                    Optional<ButtonType> result = warning.showAndWait();
                    throw new RuntimeException(e);
                }finally
                {
                    return "value";
                }



            } else if (dialogButton == cancel)
            {
                return "null";
            }

            return " ";

        });
        dialog.showAndWait().ifPresent(result ->
        {

            primaryStage.getScene().getRoot().setEffect(null);

        });

    }

    @FXML
    void setProfilePic(MouseEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select Profile Picture");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );


        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {

            String imagePath = file.toURI().toString();

            Image pic = new Image(ImageWriter(imagePath));

            ProfilePicture.setImage(pic);
        }


    }


    public String ImageWriter(String image)
    {
        String databasePic = "";
        String query = "Insert Into Image"+LblName.getText() +" (images)values('" + image + "')";
        String query1 = "Select * from Image"+LblName.getText();
        int inserter = sqlConnection.insertData(query,0);

        if(inserter == 1)
        {
            ResultSet resultSet = sqlConnection.readDatbase(query1);
            try
            {

               while(resultSet.next())
               {

                   databasePic = resultSet.getString("images");
               }

            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

        }

        return databasePic;
    }

    @FXML
    void ZoomPic(MouseEvent event)
    {
        newImageHolder();

        Image image = ProfilePicture.getImage();


        ImageView profiles = new ImageView(image);


        profiles.setFitWidth(displayContent.getWidth());
        profiles.setFitHeight(displayContent.getHeight());

        for (Node node : displayContent.getChildren())
        {
            if (node instanceof ImageView)
            {
                displayContent.getChildren().remove(node);

                break;
            }
        }

        displayContent.getChildren().add(profiles);

    }
    public String getCurrentAcademicyear()
    {
        return currentAcademicyear.getText();
    }

    public String getAcademicyear()
    {
        return this.Academicyear = currentAcademicyear.getText();
    }

    public void receivingAcYear(AddingSemestersNmodCotroller controller)
    {
        controller.setAcademicYear(currentAcademicyear.getText());
    }

    @FXML
    void logout(MouseEvent event)throws Exception
    {
        Stage stage = (Stage) displayContent.getScene().getWindow();
        stage.close();
        stage = new Stage();
        MainOperation.pageLoader(stage,"StartHomepage.fxml","-login",1660,800);
    }

    public void YearToStorage(String year)
    {
        String query = "Insert into yearss values('" + year + "')";

       int check =  sqlConnection.insertData(query,0);

    }

    public void yearToprofile()
    {
        String query = "Select* from yearss";
        ResultSet resultSet = sqlConnection.readDatbase(query);
        String year = "";

        try
        {

            while(resultSet.next())
            {
                year =  resultSet.getString("year");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally
        {
            currentAcademicyear.setText(year);
        }
    }

    public void setProfilePrivious()
    {
        String databasePic = "";
        String query1 = "Select * from Images";// + LblName.getText();
        ResultSet resultSet = sqlConnection.readDatbase(query1);

            try
            {
                while(resultSet.next())
                {

                    databasePic = resultSet.getString("images");
                }

            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

            Image pic = new Image(databasePic);

        ProfilePicture.setImage(pic);


    }
    public void newImageHolder()
    {
        String name = LblName.getText();


        String newImage = "Select* from Image" + name;

        boolean value = sqlConnection.createTable(newImage,false);

        if(value == false)
        {
            Alert welcom = new Alert(Alert.AlertType.CONFIRMATION);
            welcom.setContentText("welcome back " + name );
            welcom.show();

        } else if (value == true)
        {

        }

        setProfilePrivious();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setProfilePrivious();//setting the profile before user
        yearToprofile();//setting a year to profile

    }

    public void setUserName(String name)
    {
        this.userName = name;

        LblName.setText(userName);
        System.out.println(userName);
    }

    public String getUserName()
    {
        System.out.println(userName);
        return userName;
    }
}
