package com.example.limkokwingsystemmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddingSemestersNmodCotroller {

    @FXML
    private AnchorPane AncId;

    @FXML
    private Text EndLblId;

    @FXML
    private Text LblCodeId;

    @FXML
    private Text LblHrsId;

    @FXML
    private Text LblModId;

    @FXML
    private Button SavebtnId;

    @FXML
    private Text StartLblId;

    @FXML
    private TextField StartTxtId;

    @FXML
    private Text TermLblId;

    @FXML
    private TextField TermTxtId;

    @FXML
    private TextField TxtCodeId;

    @FXML
    private TextField TxtHrsId;

    @FXML
    private TextField TxtModId;

    @FXML
    private TextField endDateID;

    private String academicYear;

    @FXML
    void saveSemNm(ActionEvent event)
    {


        String TermTxt = TermTxtId.getText();
        String StartTxt = StartTxtId.getText();
        String endDate = endDateID.getText();
        String TxtMod  = TxtModId.getText();
        String TxtCode = TxtCodeId.getText();
        String TxtHrs = TxtHrsId.getText();
        String AcamYear = getAcademicYear();

        if(TermTxt.isEmpty() || StartTxt.isEmpty()|| endDate.isEmpty() || TxtMod.isEmpty() || TxtCode.isEmpty() || TxtHrs.isEmpty() || AcamYear.isEmpty())
        {
            Alert confirmation = new Alert(Alert.AlertType.ERROR);
            confirmation.setContentText("fill in all the spaces");
            confirmation.showAndWait();

            return;
        }
        System.out.println("watch");

            String QuerytoSemesters = "insert into Semesters (semesterName,start_date,end_date,year)values('" + TermTxt + "','" + StartTxt + "','" + endDate + "','" + AcamYear + "')";
            String QuerytoModules = "insert into modules values('" + TxtMod + "','" + TxtCode + "','" + TxtHrs + "','" + TermTxt + "')";
            String deleteMod = "delete from modules where courseName = '" + TxtMod + "'";
            String deleteSem = "delete from semesters where start_date = '" + StartTxt + "'";


            int semesters = sqlConnection.insertData(QuerytoSemesters, 0);
            int modules = sqlConnection.insertData(QuerytoModules, 0);


            if (semesters == 1 && modules == 1) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setContentText("saved!");
                confirmation.showAndWait();

            } else {
                int rollBack = sqlConnection.insertData(deleteMod, 0);
                int rollback = sqlConnection.insertData(deleteSem, 0);

                Alert confirmation = new Alert(Alert.AlertType.ERROR);
                confirmation.setContentText("something went wrong " + rollBack + " undone");
                confirmation.showAndWait();
            }
        }

    public void setAcademicYear(String year)
    {
        this.academicYear = year;

    }

    public String getAcademicYear()
    {
        return academicYear;
    }

}