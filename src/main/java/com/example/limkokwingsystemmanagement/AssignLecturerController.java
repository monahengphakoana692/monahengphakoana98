package com.example.limkokwingsystemmanagement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AssignLecturerController implements Initializable
{

    @FXML
    private Text ClassId;

    @FXML
    private TextField ClassTxtId;

    @FXML
    private Text LecNamId;

    @FXML
    private TextField LecNamTxtId;

    @FXML
    private Button LecSavBtnId;

    @FXML
    private Text ModId;

    @FXML
    private ChoiceBox<String> modChoice;

    @FXML
    private ChoiceBox<String> LecChoice;

    @FXML
    private TextArea modDisplay;

    @FXML
    private TextField ModTxtId;

    @FXML
    private Text RoleId;

    @FXML
    private TextField RoleTxtId;

    @FXML
    private TextField password;

    @FXML
    private ChoiceBox<String> roleChoice;

    private int modsTracker = 0;

    @FXML
    private Label warnLabel;

    private List<String> userMods = new ArrayList<>();
    private List<String> Lectures = new ArrayList<>();
    private String[] Roles = {"Faculty Admin","Principal Lecturer","Lecturer"};



    @FXML
    void LecturerAssigning(ActionEvent event)
    {

        String name = LecChoice.getValue();
        String role = roleChoice.getValue();
        String module = modChoice.getValue();
        String Class = ClassTxtId.getText();
        String Pass = password.getText();

        if(name.isEmpty() || role.isEmpty() || module.isEmpty() || Class.isEmpty()  || Pass.isEmpty())
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("fill in all fields");
            error.showAndWait();
            checkListModules(userMods.toArray(new String[0]));
            checkListLectures(Lectures.toArray(new String[0]));

                 return;

        }
        if(!Pass.equals(name))
        {
            warnLabel.setText("pass must be the name ");
            return;
        }
        String query = "insert into roles values('" + name + "','" + role + "','" + module + "','" + Class +"','" + Pass + "')";


        int verify = sqlConnection.insertData(query,0);

        if(verify == 1)
        {
            Alert comfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            comfirmation.setContentText("assignment success!");
            comfirmation.showAndWait();
        }





    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        modChoice.getItems().addAll(modules());
        LecChoice.getItems().addAll(Lectures());
        roleChoice.getItems().addAll(Roles);
        modChoice.setOnAction(this :: getModules);
        LecChoice.setOnAction(this :: getLectures);
        roleChoice.setOnAction(this :: getRoles);
        LecSavBtnId.setOnAction(this::LecturerAssigning);
    }

    public void getModules(ActionEvent event)
    {
        String mods = modChoice.getValue();

        modChoice.setValue(mods);

        userMods.add(mods); // Addign the new module to the list


    }
    public void getRoles(ActionEvent event)
    {
        String roles = roleChoice.getValue();

        roleChoice.setValue(roles);

    }

    public void getLectures(ActionEvent event)
    {
        String lecs = LecChoice.getValue();

        LecChoice.setValue(lecs);

        Lectures.add(lecs); // Addign the new module to the list


    }

    public List<String> modules()
    {
        String query = "Select courseName from modules";
        String runningData;

        ResultSet mods = sqlConnection.readDatbase(query);
        List<String> modlist = new ArrayList<>();

        try
        {
            while(mods.next())
            {
                runningData = mods.getString("courseName");

                modlist.add(runningData);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return modlist;
    }

    public List<String> Lectures()
    {
        String query = "select Names from Lecturers";
        String runningData;

        ResultSet mods = sqlConnection.readDatbase(query);
        List<String> Leclist = new ArrayList<>();

        try
        {
            while(mods.next())
            {
                runningData = mods.getString("Names");

                Leclist.add(runningData);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return Leclist;
    }

    public void checkListModules(String[] array)
    {
        for (String mod : array)
        {
            System.out.println(mod);
        }
    }

    public void checkListLectures(String[] array)
    {
        for (String mod : array)
        {
            System.out.println(mod);
        }
    }
}
