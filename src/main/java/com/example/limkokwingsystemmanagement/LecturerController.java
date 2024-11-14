package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

public class LecturerController implements Initializable
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

    private String LectureName;
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
            FXMLLoader load = new FXMLLoader(getClass().getResource("Attendence.fxml"));

            Pane form = load.load();

            Attendence controller = load.getController();

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
        String query = "Insert Into Images (images)values('" + image + "')";
        String query1 = "Select * from Images";
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

    public String getLectureName()
    {
        return this.LectureName = LblName.getText();
    }

    public void receivingAcYear(Attendence controller)
    {
        System.out.println(getLectureName());
        controller.setLecturerName(getLectureName());
    }

    @FXML
    void logout(MouseEvent event)throws Exception
    {
        Stage stage = (Stage) displayContent.getScene().getWindow();
        stage.close();
        stage = new Stage();
        MainOperation.pageLoader(stage,"StartHomepage.fxml","-login",1660,800);
    }

    public void setUserName(String name)
    {

        this.userName = name;
        LblName.setText(userName);  // Update LblName directly here
    }

    public void setProfilePrivious()
    {
        String databasePic = "";
        String query1 = "Select * from Images";
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProfilePrivious();//setting the profile before user
        yearToprofile();//setting a year to profile

    }
}
