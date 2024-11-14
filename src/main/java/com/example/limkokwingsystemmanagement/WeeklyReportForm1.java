package com.example.limkokwingsystemmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WeeklyReportForm1 {

    @FXML
    private TextArea Submittedfield;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextArea challengesField;

    @FXML
    private TextField classField;

    @FXML
    private TextField moduleField;

    @FXML
    private TextArea recommendationsField;

    @FXML
    void submitWeeklyReport(ActionEvent event) {

        String className = classField.getText();
        String module = moduleField.getText();
        String challenges = challengesField.getText();
        String recommendations = recommendationsField.getText();
        String submitted = btnSubmit.getText();

        // Validate input
        if (className.isEmpty() || module.isEmpty() || challenges.isEmpty() || recommendations.isEmpty())
        {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        String query = "Insert into weekly_reports (class_name,module_code,challenges,recommendations) values('"+ className +"','" + module + "','" + challenges + "','" + recommendations +"')";
        int ni = sqlConnection.insertData(query,0);
        if (ni==1)
        {
            showAlert("Report Submitted", "Your weekly report has been submitted successfully.");
        }
        else
        {
            showAlert("Error", "oops.");
        }
    }

    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

