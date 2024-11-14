package com.example.limkokwingsystemmanagement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attendence
{
    @FXML
    private Text AdminId;

    @FXML
    private ImageView CamImage;

    @FXML
    private Circle CircleId;

    @FXML
    private Label LblName;

    @FXML
    private ImageView ProfilePicture;

    @FXML
    private Rectangle RectangePane;

    @FXML
    private Rectangle RectangleId;

    @FXML
    private Text TtalModNumId;

    @FXML
    private Text TtlLectId;

    @FXML
    private Text TtlLectNumId;

    @FXML
    private Text TtlModId;

    @FXML
    private Text TtlStId;

    @FXML
    private Text TtlStuNumId;

    @FXML
    private Label currentAcademicyear;

    @FXML
    private Pane displayContent;

    @FXML
    private ImageView imageBackGround;

    @FXML
    private Pane sidepainid;

    DatabaseConnection1 connectNow = new DatabaseConnection1();
    Connection connectDB = connectNow.getConnection();

    @FXML
    private Button btndisplay;


    @FXML
    private ListView<String> classListView;

    @FXML
    private TableView<StudentAttendance> attendanceTable;

    @FXML
    private TableColumn<StudentAttendance, String> studentNameColumn;

    @FXML
    private TableColumn<StudentAttendance, Boolean> presentColumn;

    @FXML
    private TableColumn<StudentAttendance, Boolean> absentColumn;

    @FXML
    private TableColumn<StudentAttendance, String> timestampColumn;


    @FXML
    private Button btnShowModuleOutline;

    @FXML
    private TableView<ChapterWithOutcomes> moduleOutlineTable;

    @FXML
    private TableColumn<ChapterWithOutcomes, String> chapterNameColumn;


    @FXML
    private TableColumn<ChapterWithOutcomes, String> learningOutcomesColumn;

    @FXML
    private TableColumn<ChapterWithOutcomes, Integer> chapterNumberColumn;

    @FXML
    private TableColumn<ChapterWithOutcomes, String> chapterDescriptionColumn;

    private String LecturerName;


    @FXML
    void Display(ActionEvent event)
    {
        classListView.getItems().clear();
        String name = getLecturerName();

        String connectQuery = "SELECT classes FROM roles where Name = '" + name +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            ObservableList<String> classNames = FXCollections.observableArrayList();
            while (queryOutput.next()) {
                String className = queryOutput.getString("classes");
                classNames.add(className);
            }
            classListView.setItems(classNames);

            // Set listener to load students when a class is selected
            classListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    displayStudentsInClass(newSelection);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            btndisplay.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    void initialize()
    {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        presentColumn.setCellValueFactory(new PropertyValueFactory<>("present"));
        absentColumn.setCellValueFactory(new PropertyValueFactory<>("absent"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        chapterNumberColumn.setCellValueFactory(new PropertyValueFactory<>("chapterNumber"));
        chapterNameColumn.setCellValueFactory(new PropertyValueFactory<>("chapterName"));
        chapterDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("chapterDescription"));
        learningOutcomesColumn.setCellValueFactory(new PropertyValueFactory<>("learningOutcomes"));


        // Initially hide the module outline table
        moduleOutlineTable.setVisible(false);

        // Create custom cell for present column with clickable checkboxes
        presentColumn.setCellFactory(param -> new TableCell<StudentAttendance, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(checkBox);
                    checkBox.setSelected(item != null && item);  // Set the checkbox state based on the Boolean value
                    checkBox.setOnAction(event -> {
                        // Update the underlying property when the checkbox is clicked
                        StudentAttendance student = getTableRow().getItem();
                        if (student != null) {
                            student.setPresent(checkBox.isSelected());
                        }
                    });
                }
            }
        });

        // Create custom cell for absent column with clickable checkboxes
        absentColumn.setCellFactory(param -> new TableCell<StudentAttendance, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(checkBox);
                    checkBox.setSelected(item != null && item);  // Set the checkbox state based on the Boolean value
                    checkBox.setOnAction(event -> {
                        // Update the underlying property when the checkbox is clicked
                        StudentAttendance student = getTableRow().getItem();
                        if (student != null) {
                            student.setAbsent(checkBox.isSelected());
                        }
                    });
                }
            }
        });
    }


    private void displayStudentsInClass(String className)
    {
        ObservableList<StudentAttendance> studentList = FXCollections.observableArrayList();

        String studentQuery = "SELECT Names FROM students WHERE ClassName = '" + className + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(studentQuery);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTimestamp = LocalDateTime.now().format(formatter);


            while (queryOutput.next()) {
                String studentName = queryOutput.getString("Names");
                studentList.add(new StudentAttendance(studentName, false, false, currentTimestamp));
            }
            attendanceTable.setItems(studentList);

            // Add listeners to each student's attendance checkboxes to save to the database
            for (StudentAttendance student : studentList) {
                student.presentProperty().addListener((obs, wasPresent, isNowPresent) -> {
                    if (isNowPresent) {
                        student.setAbsent(false); // Uncheck "Absent" if "Present" is checked
                        saveAttendanceToDatabase(student.getName(), "Present");
                    }
                });
                student.absentProperty().addListener((obs, wasAbsent, isNowAbsent) -> {
                    if (isNowAbsent) {
                        student.setPresent(false); // Uncheck "Present" if "Absent" is checked
                        saveAttendanceToDatabase(student.getName(), "Absent");
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            btndisplay.setText("Error: " + e.getMessage());
        }
    }

    private void saveAttendanceToDatabase(String studentName, String status) {
        // Format the date without the time component
        String dateOnly = LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Check if an attendance record already exists for this student on the same date
        String checkQuery = "SELECT * FROM Attendance WHERE StudentName = ? AND DATE(Timestamp) = ?";
        String insertQuery = "INSERT INTO Attendance (StudentName, Status, Timestamp) VALUES (?, ?, ?)";

        try {
            // Prepare statement to check for existing records
            PreparedStatement checkStmt = connectDB.prepareStatement(checkQuery);
            checkStmt.setString(1, studentName);
            checkStmt.setString(2, dateOnly);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // If a record exists, show an alert message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Attendance");
                alert.setHeaderText(null);
                alert.setContentText("Attendance for " + studentName + " has already been recorded today.");
                alert.showAndWait();
            } else {
                // No record exists, proceed to insert the new attendance record
                PreparedStatement insertStmt = connectDB.prepareStatement(insertQuery);
                insertStmt.setString(1, studentName);
                insertStmt.setString(2, status);
                insertStmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                insertStmt.executeUpdate();

                // Update the timestamp in the TableView
                for (StudentAttendance student : attendanceTable.getItems()) {
                    if (student.getName().equals(studentName)) {
                        student.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class StudentAttendance {
        private final SimpleStringProperty name;
        private final SimpleBooleanProperty present;
        private final SimpleBooleanProperty absent;
        private final SimpleStringProperty timestamp;

        public StudentAttendance(String name, boolean present, boolean absent, String timestamp) {
            this.name = new SimpleStringProperty(name);
            this.present = new SimpleBooleanProperty(present);
            this.absent = new SimpleBooleanProperty(absent);
            this.timestamp = new SimpleStringProperty(timestamp);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public boolean isPresent() {
            return present.get();
        }

        public SimpleBooleanProperty presentProperty() {
            return present;
        }

        public void setPresent(boolean present) {
            this.present.set(present);
        }

        public boolean isAbsent() {
            return absent.get();
        }

        public SimpleBooleanProperty absentProperty() {
            return absent;
        }

        public void setAbsent(boolean absent) {
            this.absent.set(absent);
        }

        public String getTimestamp() {
            return timestamp.get();
        }

        public SimpleStringProperty timestampProperty() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp.set(timestamp);
        }
    }


    @FXML
    void ShowModuleOutline(ActionEvent event) {
        String selectedClass = classListView.getSelectionModel().getSelectedItem();

        if (selectedClass == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a class first.");
            alert.show();
            return;
        }

        ObservableList<ChapterWithOutcomes> moduleOutlineData = FXCollections.observableArrayList();
        DatabaseConnection1 dbConnection = new DatabaseConnection1();
        Connection connection = dbConnection.getConnection();

        String queryChapters = "SELECT ChapterID, ChapterNumber, ChapterName, ChapterDescription FROM Chapters WHERE ClassName = ?";
        String queryLearningOutcomes = "SELECT LearningOutcomeDescription FROM LearningOutcomes WHERE ChapterID = ?";

        try (PreparedStatement stmtChapters = connection.prepareStatement(queryChapters);
             PreparedStatement stmtOutcomes = connection.prepareStatement(queryLearningOutcomes)) {

            stmtChapters.setString(1, selectedClass);
            ResultSet rsChapters = stmtChapters.executeQuery();

            while (rsChapters.next()) {
                int chapterID = rsChapters.getInt("ChapterID");
                int chapterNumber = rsChapters.getInt("ChapterNumber");
                String chapterName = rsChapters.getString("ChapterName");
                String chapterDescription = rsChapters.getString("ChapterDescription");

                stmtOutcomes.setInt(1, chapterID);
                ResultSet rsOutcomes = stmtOutcomes.executeQuery();

                StringBuilder outcomes = new StringBuilder();
                while (rsOutcomes.next()) {
                    String outcomeDescription = rsOutcomes.getString("LearningOutcomeDescription");
                    outcomes.append(outcomeDescription).append("\n");
                }

                moduleOutlineData.add(new ChapterWithOutcomes(chapterNumber, chapterName, chapterDescription, outcomes.toString()));
            }

            moduleOutlineTable.setItems(moduleOutlineData);
            attendanceTable.setVisible(false);
            classListView.setVisible(false);
            moduleOutlineTable.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error fetching module outline: " + e.getMessage());
            alert.show();
        }
    }

    public void setLecturerName(String name)
    {
        this.LecturerName = name;

    }

    public String getLecturerName()
    {
        return LecturerName;
    }


}
