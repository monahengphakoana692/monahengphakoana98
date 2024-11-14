package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PrincipalLecturerController implements Initializable {

    @FXML
    private Label LblName;

    @FXML
    private Text TtlStuNumId;

    @FXML
    private Pane displayContent;

    @FXML
    private ImageView ProfilePicture;

    @FXML
    private Pane sidepainid;

    @FXML
    private Text txtDModified;

    @FXML
    private Text txtPrincipal;

    @FXML
    private Text txtTClasses;

    @FXML
    private Text txtTModules;

    @FXML
    private Text txtTclasses;

    @FXML
    private Text txtTmodules;

    @FXML
    private Text txtloadViewReports;

    @FXML
    private Text txtloadWeeklyReport;

    @FXML
    private Label currentAcademicyear;

    @FXML
    private Text txtlogout;
    private String userName;

    /*@FXML
    void loadViewReports(MouseEvent event)throws Exception
    {
        FXMLLoader View = new FXMLLoader(getClass().getResource("viewreports.fxml"));
        Pane reports = View.load();
        displayContent.getChildren().clear();
        displayContent.getChildren().add(reports);



    }*/

    @FXML
    void loadWeeklyReport(MouseEvent event)throws Exception
    {
        FXMLLoader rep = new FXMLLoader(getClass().getResource("WeeklyReportsform1.fxml"));
        Pane Weekly = rep.load();
        displayContent.getChildren().clear();
        displayContent.getChildren().add(Weekly);
        //LblName.setText("brhuuuu");

    }

    @FXML
    void logout(MouseEvent event)throws Exception
    {
        Stage stage = (Stage) displayContent.getScene().getWindow();
        stage.close();
        stage = new Stage();
        MainOperation.pageLoader(stage,"StartHomepage.fxml","-login",1660,800);
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
