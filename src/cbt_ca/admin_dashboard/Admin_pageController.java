/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.admin_dashboard;

import cbt_ca.reusable_functions.CSVAccessMethods;
import cbt_ca.reusable_functions.centerScreen;
import cbt_ca.reusable_functions.InputMethods;
import cbt_ca.reusable_functions.imageUpload;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.components.course.courseFunctions;
import cbt_ca.models.student_model;
import cbt_ca.models.lecturer_model;
import cbt_ca.models.question_model;
import cbt_ca.models.student_score_model;
import cbt_ca.reusable_functions.PasswordGenerator;
import cbt_ca.reusable_functions.mailSender;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author HP PROBOOK 430 G3
 */
public class Admin_pageController implements Initializable {

    @FXML
    private Circle profile_photo, activeRegistrationTypeCircle;
    @FXML
    private VBox logOutButton, errorDialogBox, successDialogBox,indivudualUploadTab, bulkUploadTab, indivudualLecturerUploadTab1;
    @FXML
    private HBox register_button, tests_button, students_button, individualUploadButton, bulkUploadButton, uploadButton, tableContainer;
    @FXML
    private BorderPane register_side, test_side, students_side,fromContainer;
    
    private final Map<HBox, BorderPane> buttonPaneMap = new HashMap<>();
    private final Map<TextField, Label> warningIndividualMap = new HashMap<>();
    private final Map<TextField, Label> warningLecturerMap = new HashMap<>();
    private final Map<HBox, StackPane> uploadTabsMaps = new HashMap<>();
    private final Map<TextField, Label> warningCourseMap = new HashMap<>();
    private final Map<HBox, Region> questionMap = new HashMap<>();
    private final Map<TextField, Label> warningTestMap = new HashMap<>();
    private final Map<HBox, VBox> studentScoresMap = new HashMap<>();
    
    @FXML
    private StackPane userImage;
    @FXML
    private Label staffIDWarning1;
    @FXML
    private Label emailWarning1, phoneNumberWarning1, lastNameWarning1, firstNameWarning1, lecturrRegisterButton, studentRegisterButton, staffIDwarning, fullName, emailAddress, firstNameWarning, lastNameWarning, phoneNumberWarning, matNoWarning, emailWarning, staffIDWarning, errorMessage;
    
    imageUpload imageChooser = new imageUpload();
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private ComboBox<String> levelComboBox;
    @FXML
    private ComboBox<String> departmentComboBox, lecturerDepartmentComboBox;
    @FXML
    private TextField lecturerStaffIDInput, lecturerEmailInput, lecturerPhoneNumberInput, lecturerLastNameInput, firstNameInput, lastNameInput, phoneNumberInput, matNoInput, emailInput, staffIDInput, staffIDField, lecturerFirstNameInput;
    
    private final InputMethods currentInputMethods;
    private final databaseConnection currentConnection;
    private final CSVAccessMethods newCSVAccess;
    private final uniqueAdminPageFunctions uniqueAdminMethods;
    private final courseFunctions currentCourseFunction;
    private final PasswordGenerator newPasswordGenerator;
    private TranslateTransition translateTransition;
    private mailSender currentMailSender;
    @FXML
    private TableView<student_model> adminRegisterTable;
    @FXML
    private TableColumn<student_model, String> first_name_column;
    @FXML
    private TableColumn<student_model, String> last_name_column;
    @FXML
    private TableColumn<student_model, String> phone_number_column;
    @FXML
    private TableColumn<student_model, String> mat_number_column;
    @FXML
    private TableColumn<student_model, LocalDate> date_of_birth_column;
    @FXML
    private TableColumn<student_model, String> gender_column;
    @FXML
    private TableColumn<student_model, String> level_column;
    @FXML
    private TableColumn<student_model, String> department_column;
    @FXML
    private TableColumn<student_model, String> email_column;
    @FXML
    private HBox lecturerTableContainer;
    @FXML
    private TextField staffIDFieldLecturer;
    @FXML
    private Label staffIDwarningLecturer;
    @FXML
    private TableView<lecturer_model> adminRegisterTableLecturer;
    @FXML
    private TableColumn<lecturer_model, String> first_name_column_lecturer;
    @FXML
    private TableColumn<lecturer_model, String> last_name_column_lecturer;
    @FXML
    private TableColumn<lecturer_model, String> phone_number_column_lecturer;
    @FXML
    private TableColumn<lecturer_model, String> department_column_lecturer;
    @FXML
    private TableColumn<lecturer_model, String> email_column_lecturer;
    @FXML
    private StackPane individualStackPane;
    @FXML
    private StackPane bulkStackPane;
    @FXML
    private ScrollPane courseListScrollPane;
    @FXML
    private VBox courseContainerVBox;
    @FXML
    private VBox noContentVBox;
    @FXML
    private AnchorPane listOfCoursesContainerAnchorPane;
    @FXML
    private VBox registerCourseContainer;
    @FXML
    private TextField courseName;
    @FXML
    private Label courseNameWarning;
    @FXML
    private TextField courseCode;
    @FXML
    private Label courseCodeWarning;
    @FXML
    private ComboBox<String> courseDepartmentComboBox;
    @FXML
    private ComboBox<String> courseLevelComboBox;
    @FXML
    private TextField courseStaffIDInput;
    @FXML
    private Label staffIDCourseWarning;
    @FXML
    private StackPane registerCoursesStackPane;
    @FXML
    private BorderPane testConfigBorderPane;
    @FXML
    private TableView<question_model> questionsTable;
    @FXML
    private TableColumn<question_model, String> question_column;
    @FXML
    private TableColumn<question_model, String> option_a_column;
    @FXML
    private TableColumn<question_model, String> option_b_column;
    @FXML
    private TableColumn<question_model, String> option_c_column;
    @FXML
    private TableColumn<question_model, String> option_d_column;
    @FXML
    private TableColumn<question_model, String> correct_option_column;
    @FXML
    private HBox setQuestionTab;
    @FXML
    private HBox setTestTab;
    @FXML
    private BorderPane setQuestionBorderPane;
    @FXML
    private Pane setTestPane;
    @FXML
    private TextField testNameInput;
    @FXML
    private Label testNameWarningTest;
    @FXML
    private ComboBox<Integer> hoursComboBox;
    @FXML
    private ComboBox<Integer> minutesComboBox;
    @FXML
    private DatePicker testDatePicker;
    @FXML
    private ComboBox<Integer> numberOfQuestionsComboBox;
    
    
    private String uniqueStaffID = null;
    private String courseID;
    private String courseNameForTest;
    private String courseCodeForTest;
    private String departmentForTest;
    private String levelForTest;
    @FXML
    private ComboBox<Integer> hourTimeComboBox;
    @FXML
    private ComboBox<Integer> minuteTimeComboBox;
    @FXML
    private HBox studentRecordTabButton;
    @FXML
    private TextField firstNameSearchValue;
    @FXML
    private Label firstNameSearchWarning;
    @FXML
    private TextField lastNameSearchValue;
    @FXML
    private Label lastNameSearchWarning;
    @FXML
    private TextField phoneNumberSearchValue;
    @FXML
    private Label phoneNumberSearchWarning;
    @FXML
    private TextField matNoSearchValue;
    @FXML
    private Label matNoSearchWarning;
    @FXML
    private DatePicker birthDatePickerSearchValue;
    @FXML
    private ComboBox<String> genderSearchValue;
    @FXML
    private ComboBox<String> levelSearchValue;
    @FXML
    private ComboBox<String> departmentSearchValue;
    @FXML
    private TextField emailSearchValue;
    @FXML
    private Label emailWarning2;
    @FXML
    private Button resetUserPasswordButton;
    @FXML
    private TextField matNumberQueryField;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button updateButton;
    @FXML
    private VBox studentDetailsTab;
    @FXML
    private Button downlooadTableButton;
    @FXML
    private TableView<student_score_model> testScoreTable;
    @FXML
    private TableColumn<student_score_model, String> test_name_column;
    @FXML
    private TableColumn<student_score_model, String> number_of_questions_column;
    @FXML
    private TableColumn<student_score_model, String> score_column;
    @FXML
    private TableColumn<student_score_model, String> selected_optons_column;
    @FXML
    private TableColumn<student_score_model, String> mat_number_score_column;
    @FXML
    private HBox studentScoresTabButton;
    @FXML
    private VBox studentTestScoresTab;
    @FXML
    private ComboBox<String> filterMode;
    @FXML
    private Label sucessMessage;


    public Admin_pageController() throws ClassNotFoundException, SQLException {
        this.currentInputMethods = new InputMethods();
        this.currentConnection = new databaseConnection();
        this.newCSVAccess = new CSVAccessMethods();
        this.uniqueAdminMethods = new uniqueAdminPageFunctions();
        this.currentCourseFunction = new courseFunctions();
        this.newPasswordGenerator = new PasswordGenerator();
        this.currentMailSender = new mailSender();
    }
    
    public void setData(HashMap<String, String> data) throws IOException, ClassNotFoundException, SQLException {
        fullName.setText(data.get("firstName") + " " + data.get("lastName"));
        emailAddress.setText(data.get("emailAddress"));
        uniqueStaffID = data.get("staffID");
        currentConnection.getAllScoresFromDatabase(uniqueStaffID, testScoreTable, filterMode, downlooadTableButton);
        
        currentCourseFunction.initializeCourseList(uniqueStaffID, noContentVBox, courseListScrollPane, courseContainerVBox, this);
        try {
            Image profileImage = currentConnection.imageDownload("lecturer_details", data.get("emailAddress"));
            if(profileImage == null){ 
                profileImage = new Image(getClass().getResource("../images/userIcon.png").toExternalForm(), 150, 150, true, true);
            }
            profile_photo.setFill(new ImagePattern(profileImage));
        } catch (SQLException ex) {
            Logger.getLogger(Admin_pageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openSetTestPage(String newCourseID, String courseNameParam, String courseCodeParam, String departmentParam, String levelParam) throws SQLException{
        courseID = newCourseID;
        courseNameForTest = courseNameParam;
        courseCodeForTest = courseCodeParam;
        departmentForTest = departmentParam;
        levelForTest = levelParam;
        newCSVAccess.getDataFromDatabase(courseID, questionsTable, currentConnection);
        registerCoursesStackPane.setVisible(false);
        testConfigBorderPane.setVisible(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        translateTransition = new TranslateTransition(Duration.millis(500), activeRegistrationTypeCircle);
        newCSVAccess.initialTable(first_name_column, last_name_column, phone_number_column, mat_number_column, date_of_birth_column, gender_column, level_column, department_column, email_column);
        newCSVAccess.initialTable(first_name_column_lecturer, last_name_column_lecturer, phone_number_column_lecturer , department_column_lecturer, email_column_lecturer);
        newCSVAccess.initilizeStudentScoresTable(test_name_column, number_of_questions_column, score_column, mat_number_score_column, selected_optons_column);
        newCSVAccess.initialQuestionTable(question_column, option_a_column, option_b_column , option_c_column, option_d_column, correct_option_column);
        uniqueAdminMethods.initializeButtonPaneMap(genderComboBox, levelComboBox, departmentComboBox, buttonPaneMap, warningIndividualMap, uploadTabsMaps, staffIDInput, firstNameInput, lastNameInput, emailInput, phoneNumberInput, matNoInput, individualUploadButton, bulkUploadButton, register_button, tests_button, students_button, students_side, test_side, register_side, firstNameWarning, lastNameWarning, phoneNumberWarning, matNoWarning, emailWarning, staffIDWarning, indivudualUploadTab, bulkUploadTab, individualStackPane, bulkStackPane, courseDepartmentComboBox, courseLevelComboBox, hoursComboBox, minutesComboBox, numberOfQuestionsComboBox, warningTestMap, testNameInput, testNameWarningTest, hourTimeComboBox, minuteTimeComboBox, studentScoresMap, studentScoresTabButton, studentRecordTabButton, studentDetailsTab, studentTestScoresTab);
        uniqueAdminMethods.initializeLecturerMap(lecturerDepartmentComboBox, warningLecturerMap, lecturerStaffIDInput, lecturerLastNameInput, lecturerFirstNameInput, lecturerEmailInput, lecturerPhoneNumberInput, firstNameWarning1, lastNameWarning1, phoneNumberWarning1, emailWarning1, staffIDWarning1, warningCourseMap, courseName, courseNameWarning, courseCode, courseCodeWarning, courseStaffIDInput, staffIDCourseWarning, setQuestionTab, setTestTab, setQuestionBorderPane, setTestPane, questionMap, genderSearchValue, levelSearchValue, departmentSearchValue);
        birthDatePicker.setValue(LocalDate.now());
        testDatePicker.setValue(LocalDate.now());
        
        matNumberQueryField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the length is 11 characters
            searchButton.setDisable(newValue.length() != 11);
        });
    }   
    
    @FXML
    private void logOutUser(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
        centerScreen currentScreen = new centerScreen();
        currentScreen.centerFrame(currentStage, "/cbt_ca/login/login.fxml");
    }

    @FXML
    private void togglePage(MouseEvent event) {
        for(HBox button : buttonPaneMap.keySet()){
            button.getStyleClass().remove("selected");
            buttonPaneMap.get(button).setVisible(false);
            if(event.getSource() == button){
                button.getStyleClass().add("selected");
                buttonPaneMap.get(button).setVisible(true);
            }
            
        }
        
    }

    @FXML
    private void setProfilePicture(MouseEvent event) throws ClassNotFoundException, SQLException, IOException {
        try {
            imageChooser.uploadFile(userImage, profile_photo, "lecturer_details", emailAddress.getText());
        } catch (IOException ex) {
            Logger.getLogger(Admin_pageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    @FXML
    private void registerStudent(MouseEvent event) throws SQLException, Exception {
        uniqueAdminMethods.registerOneStudent(currentInputMethods, warningIndividualMap, emailInput, phoneNumberInput, errorMessage, matNoInput, birthDatePicker, currentConnection, staffIDInput, firstNameInput, lastNameInput, genderComboBox, levelComboBox, departmentComboBox, successDialogBox, fromContainer, errorDialogBox, sucessMessage);
    }

    @FXML
    private void closeErrorDialogBox(ActionEvent event) {
        successDialogBox.setVisible(false);
        errorDialogBox.setVisible(false);
        fromContainer.setDisable(false);
    }

    @FXML
    private void uploadDocument(MouseEvent event) throws IOException {
        if(lecturrRegisterButton.getStyleClass().contains("currentlyActiveButton")){
            newCSVAccess.invertLecturerCSVToTable(uploadButton, adminRegisterTableLecturer, bulkUploadTab, lecturerTableContainer);
        }else{
            newCSVAccess.invertCSVToTable(uploadButton, adminRegisterTable, bulkUploadTab, tableContainer);
        }
    }

    @FXML
    private void switchTab(MouseEvent event) {
        
        for(HBox button : uploadTabsMaps.keySet()){
            button.getStyleClass().remove("selected_tab");
            uploadTabsMaps.get(button).setVisible(false);
            if(event.getSource() == button){
                button.getStyleClass().add("selected_tab");
                uploadTabsMaps.get(button).setVisible(true);
            }
        }
    }

    @FXML
    private void deleteOneRow(ActionEvent event) {
        int selectedIndex = adminRegisterTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            adminRegisterTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void deleteAllrows(ActionEvent event) {
        adminRegisterTable.getItems().clear();
    }

    @FXML
    private void closeTable(ActionEvent event) {
        bulkUploadTab.setVisible(true);
        tableContainer.setVisible(false);
    }

    @FXML
    private void uploadTable(ActionEvent event) throws SQLException {
        
        uniqueAdminMethods.registerManyStudents(adminRegisterTable, currentInputMethods, currentConnection, errorMessage, staffIDField, successDialogBox, staffIDwarning, bulkUploadTab, tableContainer, sucessMessage);
    }

    @FXML
    private void changeRegistrationType(MouseEvent event) {
        if(!tableContainer.isVisible() && !lecturerTableContainer.isVisible())
        uniqueAdminMethods.changeRegistrationType(translateTransition, event, lecturrRegisterButton, studentRegisterButton, indivudualUploadTab, indivudualLecturerUploadTab1, bulkUploadButton);
    }

    @FXML
    private void registerLecturer(ActionEvent event) throws SQLException, Exception {
        uniqueAdminMethods.registerOneLecturer(currentInputMethods, warningLecturerMap,  lecturerEmailInput, lecturerPhoneNumberInput, errorMessage, currentConnection, lecturerStaffIDInput, lecturerFirstNameInput, lecturerLastNameInput, lecturerDepartmentComboBox, successDialogBox, fromContainer, errorDialogBox, sucessMessage);
    }

    @FXML
    private void deleteOneRowLecturer(ActionEvent event) {
        int selectedIndex = adminRegisterTableLecturer.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            adminRegisterTableLecturer.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void deleteAllrowsLecturer(ActionEvent event) {
        adminRegisterTableLecturer.getItems().clear();
    }

    @FXML
    private void closeTableLecturer(ActionEvent event) {
        bulkUploadTab.setVisible(true);
        lecturerTableContainer.setVisible(false);
    }

    @FXML
    private void uploadTableLecturer(ActionEvent event) throws SQLException {
        uniqueAdminMethods.registerManyLecturers(adminRegisterTableLecturer, currentInputMethods, currentConnection, errorMessage, staffIDFieldLecturer, successDialogBox, staffIDwarningLecturer, bulkUploadTab, lecturerTableContainer, sucessMessage);
    }

    @FXML
    private void createANewCourse(MouseEvent event) throws SQLException {
        boolean courseCreated = uniqueAdminMethods.createNewCourse(warningCourseMap, courseName, courseCode, courseStaffIDInput, courseDepartmentComboBox, courseLevelComboBox, currentConnection, currentInputMethods, errorMessage, successDialogBox, fromContainer, errorDialogBox, sucessMessage);
        if(courseCreated){
            currentCourseFunction.refreshCourseList(uniqueStaffID, noContentVBox, courseListScrollPane, courseContainerVBox, this);
            listOfCoursesContainerAnchorPane.setVisible(true);
            registerCourseContainer.setVisible(false);
        }
    }

    @FXML
    private void closeCourseRegisterPage(MouseEvent event) {
        listOfCoursesContainerAnchorPane.setVisible(true);
        registerCourseContainer.setVisible(false);
        courseName.setText("");
        courseCode.setText("");
        courseStaffIDInput.setText("");
    }

    @FXML
    private void closeListOfCourses(MouseEvent event) {
        listOfCoursesContainerAnchorPane.setVisible(false);
        registerCourseContainer.setVisible(true);
    }

    @FXML
    private void insertQuestionFromFile(ActionEvent event) throws IOException {
        if (event.getSource() instanceof Button button) {
            newCSVAccess.convertCSVToQuestionTable(button, questionsTable);
        }
    }

    @FXML
    private void createNewRow(ActionEvent event) {
        newCSVAccess.addNewRow(questionsTable);
    }


    @FXML
    private void deleteQuestionRow(ActionEvent event) {
        int selectedIndex = questionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            questionsTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void emptyQuestionTable(ActionEvent event) {
        questionsTable.getItems().clear();
    }

    @FXML
    private void uploadTableToDatabase(ActionEvent event) throws SQLException {
        uniqueAdminMethods.insertManyQuestions(questionsTable, currentConnection, bulkUploadTab, lecturerTableContainer, courseID, successDialogBox, sucessMessage);
    }

    @FXML
    private void clolseQuestionContainer(MouseEvent event) {
        registerCoursesStackPane.setVisible(true);
        testConfigBorderPane.setVisible(false);
    }

    @FXML
    private void switchQuestionTab(MouseEvent event) {
        System.out.println("Pressed");
        for(HBox button : questionMap.keySet()){
            button.getStyleClass().remove("white_background");
            questionMap.get(button).setVisible(false);
            if(event.getSource() == button){
                button.getStyleClass().add("white_background");
                questionMap.get(button).setVisible(true);
            }
            
        }
    }

    @FXML
    private void scheduleTest(ActionEvent event) throws SQLException {
        uniqueAdminMethods.scheduleATest(currentInputMethods, warningTestMap, testNameInput, errorMessage, testDatePicker, currentConnection, hoursComboBox, minutesComboBox, numberOfQuestionsComboBox, successDialogBox, fromContainer, errorDialogBox, uniqueStaffID, courseID, courseNameForTest, courseCodeForTest, departmentForTest, levelForTest, hourTimeComboBox, minuteTimeComboBox, sucessMessage);
    }


    @FXML
    private void resetUserPassword(ActionEvent event) throws SQLException, Exception {
        String newPassword = newPasswordGenerator.generatePassword();
        System.out.println(newPassword);
        currentConnection.updatePasswprd(newPassword, matNoSearchValue.getText());
        currentMailSender.resetPasswordMail(firstNameSearchValue.getText(), currentInputMethods.complete(emailSearchValue.getText()), newPassword);
        sucessMessage.setText("Password reset successful");
        successDialogBox.setVisible(true);
        fromContainer.setDisable(false);
    }

    @FXML
    private void searchForAStudent(ActionEvent event) throws SQLException {
        ResultSet rs = currentConnection.checkIfUserExists(matNumberQueryField.getText(), "SELECT * FROM student_details WHERE mat_number = ?");
        if(rs.next()){
            firstNameSearchValue.setText( rs.getString("first_name"));
            lastNameSearchValue.setText( rs.getString("last_name"));
            phoneNumberSearchValue.setText( rs.getString("phone_number"));
            matNoSearchValue.setText(rs.getString("mat_number"));
            LocalDate currentDate = new java.sql.Date(rs.getDate("date_of_birth").getTime()).toLocalDate();
            birthDatePickerSearchValue.setValue(currentDate);
            String gender = rs.getString("gender");
            genderSearchValue.setValue(gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase());
            levelSearchValue.setValue(rs.getString("level"));
            departmentSearchValue.setValue(rs.getString("department"));
            emailSearchValue.setText(currentInputMethods.trimEmail(rs.getString("email")));
            resetUserPasswordButton.setDisable(false);
            deleteStudentButton.setDisable(false);
            updateButton.setDisable(false);
        }else{
            errorMessage.setText("No user found");
            errorDialogBox.setVisible(true);
            fromContainer.setDisable(true); 
        }
    }

    @FXML
    private void deleteTheStudent(ActionEvent event) throws SQLException {
        int rs = currentConnection.deleteStudent(matNoSearchValue.getText());
        if (rs > 0){
            sucessMessage.setText("Record removed successfully");
            successDialogBox.setVisible(true);
            fromContainer.setDisable(false);
            uniqueAdminMethods.resetStudentFields(firstNameSearchValue, lastNameSearchValue, emailSearchValue, phoneNumberSearchValue, matNumberQueryField, birthDatePickerSearchValue, genderSearchValue, levelSearchValue, departmentSearchValue, matNoSearchValue, resetUserPasswordButton, deleteStudentButton, updateButton, searchButton);
        }else{
            errorMessage.setText("An error occuered somewhere");
            errorDialogBox.setVisible(true);
            fromContainer.setDisable(true); 
        }
    }

    @FXML
    private void updateStudentRecord(ActionEvent event) throws SQLException {
        int rs = currentConnection.updateStudentInDatabase(firstNameSearchValue.getText(), lastNameSearchValue.getText(), phoneNumberSearchValue.getText(), matNumberQueryField.getText(), birthDatePickerSearchValue.getValue(), genderSearchValue.getValue(), levelSearchValue.getValue(), departmentSearchValue.getValue(), currentInputMethods.complete(emailSearchValue.getText()));
        if (rs > 0){
            sucessMessage.setText("PRecord updated successfully");
            successDialogBox.setVisible(true);
            fromContainer.setDisable(false);
            uniqueAdminMethods.resetStudentFields(firstNameSearchValue, lastNameSearchValue, emailSearchValue, phoneNumberSearchValue, matNumberQueryField, birthDatePickerSearchValue, genderSearchValue, levelSearchValue, departmentSearchValue, matNoSearchValue, resetUserPasswordButton, deleteStudentButton, updateButton, searchButton);
        }else{
            errorMessage.setText("An error occured somewhere");
            errorDialogBox.setVisible(true);
            fromContainer.setDisable(true); 
        }
    }

    @FXML
    private void downloadScoresAsCSV(ActionEvent event) {
        ObservableList<student_score_model> items = testScoreTable.getItems();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (student_score_model item : items) {
                    writer.append(item.toString() + "\n");
                }
                sucessMessage.setText("Download successful");
                successDialogBox.setVisible(true);
                fromContainer.setDisable(false);
            } catch (IOException e){
                errorMessage.setText("An error occured somewhere");
                errorDialogBox.setVisible(true);
                fromContainer.setDisable(true); 
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchStudentTab(MouseEvent event) {
        for(HBox button : studentScoresMap.keySet()){
            button.getStyleClass().remove("selected_tab");
            studentScoresMap.get(button).setVisible(false);
            if(event.getSource() == button){
                button.getStyleClass().add("selected_tab");
                studentScoresMap.get(button).setVisible(true);
            }
        }
    }
}
