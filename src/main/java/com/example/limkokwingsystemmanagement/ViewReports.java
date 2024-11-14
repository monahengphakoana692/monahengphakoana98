package com.example.limkokwingsystemmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.limkokwingsystemmanagement.JDBC.retrieveUser;

public class ViewReports {

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private TableColumn<Report, String> classColumn;

    @FXML
    private TableColumn<Report, String> moduleColumn;

    @FXML
    private TableColumn<Report, String> challengesColumn;

    @FXML
    private TableColumn<Report, String> recommendationsColumn;

    @FXML
    private TableColumn<Report, String> dateSubmittedColumn;

    @FXML
    public void initialize() throws SQLException
    {
        try

        {
            ResultSet ni = null;
            try
            {
                String query = "Select* from weekly_reports";
                ni = retrieveUser(query);
            } catch (Exception e)
            {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setContentText("wow -gs" + e.toString());
                err.showAndWait();
                throw new RuntimeException(e);

            }

            List<Report> datalist = new ArrayList<>();

            while(ni.next())
            {
                String Class = ni.getString("class_name");
                String modCode = ni.getString("module_code");
                String Challenges = ni.getString("challenges");
                String  Recommendation = ni.getString("recommendations");
                String  submission_date = ni.getString("submission_date");
                try
                {
                    datalist.add( new Report(Class,modCode,Challenges,Recommendation,submission_date));
                } catch (Exception e)
                {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setContentText("wow- r" + e.toString());
                    err.showAndWait();
                    throw new RuntimeException(e);
                }


            }


            ObservableList<Report> observableList = FXCollections.observableArrayList(datalist);
            try{
                classColumn.setCellValueFactory( new PropertyValueFactory<Report ,String>("class"));
                moduleColumn.setCellValueFactory( new PropertyValueFactory<Report ,String>("Module"));
                challengesColumn.setCellValueFactory( new PropertyValueFactory<Report ,String>("Challenge"));
                recommendationsColumn.setCellValueFactory( new PropertyValueFactory<Report ,String>("Recommendation"));
                dateSubmittedColumn.setCellValueFactory( new PropertyValueFactory<Report ,String>("Date Submitted"));




                //reportTable.setItems(observableList);
            } catch (Exception e)
            {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setContentText("wow-ur" + e.toString());
                err.showAndWait();
                throw new RuntimeException(e);
            }

        } catch (Exception e)
        {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("wow -" + e.toString());
            err.showAndWait();
            throw new RuntimeException(e);
        }


    }


}