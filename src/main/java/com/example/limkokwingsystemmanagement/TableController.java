package com.example.limkokwingsystemmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TableController {

    DatabaseConnection2 connectNow = new DatabaseConnection2();
    Connection connectDB = connectNow.getConnection();

    @FXML private TableView<LecturerRole> tableView;
    @FXML private TableColumn<LecturerRole, String> nameColumn;
    @FXML private TableColumn<LecturerRole, String> roleColumn;
    @FXML private TableColumn<LecturerRole, String> facultyColumn;
    @FXML private TableColumn<LecturerRole, String> classesColumn;
    @FXML private TableColumn<LecturerRole, String> moduleColumn;
    @FXML private TableColumn<LecturerRole, String> qualificationsColumn;
    @FXML private TableColumn<LecturerRole, String> contactsColumn;
    @FXML private TableColumn<LecturerRole, String> emailColumn;

    private ObservableList<LecturerRole> data = FXCollections.observableArrayList();

    public void initialize() {
        // Set up the TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        classesColumn.setCellValueFactory(new PropertyValueFactory<>("classes"));
        moduleColumn.setCellValueFactory(new PropertyValueFactory<>("module"));
        qualificationsColumn.setCellValueFactory(new PropertyValueFactory<>("qualifications"));
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.setItems(data);
        loadData();
    }

    private void loadData() {

        String query = """
            SELECT 
                lecturers.Names,
                roles.Role,
                lecturers.Faculty,
                roles.classes,
                roles.module,
                lecturers.qualifications,
                lecturers.contacts,
                lecturers.Email
            FROM 
                roles
            JOIN 
                lecturers ON roles.Name = lecturers.Names;
            """;

        try (
             Statement stmt = connectDB.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("Names");
                String role = rs.getString("Role");
                String faculty = rs.getString("Faculty");
                String classes = rs.getString("classes");
                String module = rs.getString("module");
                String qualifications = rs.getString("qualifications");
                String contacts = rs.getString("contacts");
                String email = rs.getString("Email");

                LecturerRole lecturerRole = new LecturerRole(name, role, faculty, classes, module, qualifications, contacts, email);
                data.add(lecturerRole);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
