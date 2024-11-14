package com.example.limkokwingsystemmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddLecturerController {

    @FXML
    private TextField ContactsID;

    @FXML
    private TextField EmailID;

    @FXML
    private TextField FCID;

    @FXML
    private TextField QuansID;

    @FXML
    private Button addLectureID;

    @FXML
    private TextField namesID;

    @FXML
    void adLecturer(ActionEvent event)
    {
        String Names = namesID.getText();
        String Quans = QuansID.getText();
        String faculty = FCID.getText();
        String contacts = ContactsID.getText();
        String Email   = EmailID.getText();

        if(Names.isEmpty() || Quans.isEmpty() || faculty.isEmpty() || contacts.isEmpty() || Email.isEmpty())
        {
            Alert confirmation = new Alert(Alert.AlertType.ERROR);
            confirmation.setContentText(" fill all the fields");
            confirmation.showAndWait();

            return;
        }
        String writeQuery = "INSERT INTO Lecturers VALUES('" + Names + "','" + Quans + "','"+faculty +"','"+
                contacts +"','" + Email + "')";
        //String imageTable = "Create table Image" + Names + "(images varchar(5000))";
        int check = sqlConnection.insertData(writeQuery,0);
        //boolean value = sqlConnection.createTable(imageTable,false);
        //&& value == true

        if(check == 1 )
        {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setContentText(Names + ": added successfully!");
            confirmation.showAndWait();

        }else
        {
            Alert confirmation = new Alert(Alert.AlertType.ERROR);
            confirmation.setContentText(" something went wrong  with adding " + Names );
            confirmation.showAndWait();
        }
    }

}
