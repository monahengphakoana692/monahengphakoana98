package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class StartHomepageController {

    @FXML
    private AnchorPane content;

    @FXML
    private Text AdminTxt;

    @FXML
    private Rectangle Bigrec;

    @FXML
    private Text LecturerTxt;

    @FXML
    private ImageView LimLogo;

    @FXML
    private Text LoginTxt;

    @FXML
    private Text PrincipalTxt;

    @FXML
    private Rectangle Smallrec;

    @FXML
    private ImageView backgroundPic;

    @FXML
    private AnchorPane content2;

    @FXML
    void Login(MouseEvent event)
    {

        Stage stage = (Stage)content2.getScene().getWindow();
        //LoginTxt.setText("Trial");
        stage.close();
        stage = new Stage();

        try {

            MainOperation.pageLoader(stage, "Login.fxml", "Login",1000,600);

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Here");
            error.showAndWait();
            System.out.println("what");
            System.out.println(e);
        }
    }
}
