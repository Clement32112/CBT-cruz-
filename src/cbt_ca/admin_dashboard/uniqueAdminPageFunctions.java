/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.admin_dashboard;

import cbt_ca.models.credentials;
import cbt_ca.models.lecturer_model;
import cbt_ca.models.question_model;
import java.sql.ResultSet;
import java.time.LocalDate;
import cbt_ca.reusable_functions.InputMethods;
import cbt_ca.reusable_functions.mailSender;
import java.util.Map;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import cbt_ca.reusable_functions.databaseConnection;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import cbt_ca.models.student_model;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author HP PROBOOK 430 G3
 */
public class uniqueAdminPageFunctions {
    
    private final mailSender mailSender = new mailSender();
    
    public void registerOneStudent(            
        InputMethods currentInputMethods, 
        Map<TextField, Label> warningIndividualMap, 
        TextField emailInput,
        TextField phoneNumberInput,
        Label errorMessage,
        TextField matNoInput,
        DatePicker birthDatePicker,
        databaseConnection currentConnection,
        TextField staffIDInput,
        TextField firstNameInput,
        TextField lastNameInput,
        ComboBox<String> genderComboBox,
        ComboBox<String> levelComboBox,
        ComboBox<String> departmentComboBox,
        VBox successDialogBox,
        BorderPane fromContainer,
        VBox errorDialogBox,
        Label successMessage
    ) throws SQLException, Exception{
        boolean metRequirements = currentInputMethods.checkForEmptyInputFields(warningIndividualMap);
        String email = emailInput.getText().toLowerCase();
        email = currentInputMethods.trimEmail(email);
        email = currentInputMethods.complete(email);
        if (metRequirements){
            var phoneNumber = phoneNumberInput.getText();
            if ((phoneNumber.startsWith("0") && phoneNumber.length() != 11) || (!phoneNumber.startsWith("0") && phoneNumber.length() != 10)){
               errorMessage.setText("Invalid phone number");
               metRequirements = false;
            }else if(matNoInput.getText().length() < 11){
                metRequirements = false;
                errorMessage.setText("Invalid matriculation number");
            }else if(birthDatePicker.getValue().isAfter(LocalDate.now())){
                metRequirements = false;
                errorMessage.setText("You cannot set future dates");
            }else if(!currentInputMethods.checkStatus(email, "^[a-zA-Z-]+\\.[a-zA-Z-]+@pau\\.edu\\.ng$")){
                metRequirements = false;
                errorMessage.setText("You can only register students here");
            }else{
                ResultSet rs = currentConnection.validateLecturerID(staffIDInput.getText(), "SELECT * FROM lecturer_details WHERE staff_id = ?");
                if(!rs.next()){
                    metRequirements = false;
                    errorMessage.setText("Invalid lecturer ID");
                }else{
                    rs = currentConnection.checkIfUserExists(email, matNoInput.getText(), "SELECT * FROM student_details WHERE email = ? OR mat_number = ?");
                    if(rs.next()){
                        metRequirements = false;
                        errorMessage.setText("Student already exists");
                    }else{
                        String dataEntryState = currentConnection.insertStudentIntoDatabase(firstNameInput.getText().toLowerCase(), lastNameInput.getText().toLowerCase(), phoneNumber, matNoInput.getText(), birthDatePicker.getValue(), genderComboBox.getValue(), levelComboBox.getValue(), departmentComboBox.getValue(), email, "INSERT INTO student_details values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        if(dataEntryState == null){
                            metRequirements = false;
                            errorMessage.setText("An occured somewhere");
                        }else{
                            successMessage.setText("student registered successfully");
                            successDialogBox.setVisible(true);
                            fromContainer.setDisable(true);
                            mailSender.sendMail(firstNameInput.getText(), email, dataEntryState);
                            resetFields(firstNameInput, lastNameInput, emailInput, phoneNumberInput, matNoInput, staffIDInput, birthDatePicker, genderComboBox, levelComboBox, departmentComboBox);
                        }
                    }
                }  
            } 
            if(!metRequirements){
                errorDialogBox.setVisible(true);
                fromContainer.setDisable(true); 
            }
        }
    }
    
    public void initializeButtonPaneMap(
        ComboBox<String> genderComboBox,
        ComboBox<String> levelComboBox,
        ComboBox<String> departmentComboBox,
        Map<HBox, BorderPane> buttonPaneMap,
        Map<TextField, Label> warningIndividualMap,
        Map<HBox, StackPane> uploadTabsMaps,
        TextField staffIDInput,
        TextField firstNameInput,
        TextField lastNameInput,
        TextField emailInput,
        TextField phoneNumberInput,
        TextField matNoInput,
        HBox individualUploadButton,
        HBox bulkUploadButton,
        HBox register_button,
        HBox tests_button ,
        HBox students_button, 
        BorderPane students_side,
        BorderPane test_side,
        BorderPane register_side,
        Label firstNameWarning,
        Label lastNameWarning,
        Label phoneNumberWarning,
        Label matNoWarning,
        Label emailWarning,
        Label staffIDWarning,
        VBox indivudualUploadTab,
        VBox bulkUploadTab,
        StackPane individualStackPane,
        StackPane bulkStackPane,
        ComboBox<String> courseDepartmentComboBox,
        ComboBox<String> courseLevelComboBox,
        ComboBox<Integer> hoursComboBox,
        ComboBox<Integer> minutesComboBox,
        ComboBox<Integer> numberOfQuestionsComboBox,
        Map<TextField, Label> warningTestMap,
        TextField testNameInput,
        Label testNameWarningTest,
        ComboBox<Integer> hourTimeComboBox,
        ComboBox<Integer> minuteTimeComboBox,
        Map<HBox, VBox> studentScoresMap, 
        HBox studentScoresTabButton, 
        HBox studentRecordTabButton, 
        VBox studentDetailsTab, 
        VBox studentTestScoresTab
        
    ) {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        levelComboBox.setItems(FXCollections.observableArrayList("100", "200", "300", "400"));
        departmentComboBox.setItems(FXCollections.observableArrayList("CSC", "MEE", "EEE", "CEE"));
        courseDepartmentComboBox.setItems(FXCollections.observableArrayList("CSC", "MEE", "EEE", "CEE"));
        courseLevelComboBox.setItems(FXCollections.observableArrayList("100", "200", "300", "400"));
        hoursComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(0, 1, 2, 3, 4, 5)));
        minutesComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(0, 15, 30, 45, 60)));
        numberOfQuestionsComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(5, 10, 15, 20, 25,30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100)));
        genderComboBox.setValue("Male");
        levelComboBox.setValue("100");
        courseLevelComboBox.setValue("100");
        departmentComboBox.setValue("CSC");
        courseDepartmentComboBox.setValue("CSC");
        hoursComboBox.setValue(2);
        minutesComboBox.setValue(0);
        numberOfQuestionsComboBox.setValue(20);
        
        buttonPaneMap.put(register_button, register_side);
        buttonPaneMap.put(tests_button, test_side);
        buttonPaneMap.put(students_button, students_side);
        
        warningIndividualMap.put(firstNameInput, firstNameWarning);
        warningIndividualMap.put(lastNameInput, lastNameWarning);
        warningIndividualMap.put(phoneNumberInput, phoneNumberWarning);
        warningIndividualMap.put(matNoInput, matNoWarning);
        warningIndividualMap.put(emailInput, emailWarning);
        warningIndividualMap.put(staffIDInput, staffIDWarning);
        
        uploadTabsMaps.put(individualUploadButton, individualStackPane);
        uploadTabsMaps.put(bulkUploadButton, bulkStackPane);
        
        warningTestMap.put(testNameInput, testNameWarningTest);
        
        studentScoresMap.put(studentScoresTabButton, studentTestScoresTab);
        studentScoresMap.put(studentRecordTabButton, studentDetailsTab);
        
        for (int i = 0; i < 24; i++) {
            hourTimeComboBox.getItems().add(i);
        }

        for (int i = 0; i < 60; i += 5) {
            minuteTimeComboBox.getItems().add(i);
        }
        
        hourTimeComboBox.setValue(0);
        minuteTimeComboBox.setValue(0);
    }
    
    public void registerManyStudents(
            TableView adminRegisterTable,
            InputMethods currentInputMethods,
            databaseConnection currentConnection,
            Label errorMessage,
            TextField staffIDInput,
            VBox successDialogBox,
            Label staffIDWarning,
            VBox bulkUploadTab,
            HBox tableContainer,
            Label successMessage
    ) throws SQLException{
        try{
            TableView<student_model> tableView = adminRegisterTable;
            List<student_model> rowsToRemove = new ArrayList<>();
            for ( int row = 0; row < tableView.getItems().size(); row++){
                boolean metRequirements = true;
                ResultSet rs;
                String firstName = "";
                String lastName = "";
                String phoneNumber = "";
                String matNumber = "";
                LocalDate dateOfBirth = LocalDate.now();
                String gender = "";
                String level = "";
                String department = "";
                String email = "";
                String staffID = "";
                for(TableColumn<student_model, ?> column : tableView.getColumns()){
                    student_model item = tableView.getItems().get(row);
                    Object cellValue = column.getCellObservableValue(item).getValue();
                    switch (column.getText()) {
                        
                        case "first_name" -> {
                            firstName = (String) cellValue;
                            if (firstName.isEmpty()) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "last_name" -> {
                            lastName = (String) cellValue;
                            if (lastName.isEmpty()) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "phone_number" -> {
                            phoneNumber = (String) cellValue;
                            if ((phoneNumber.startsWith("0") && phoneNumber.length() != 11) || (!phoneNumber.startsWith("0") && phoneNumber.length() != 10)) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "mat_number" -> {
                            matNumber = (String) cellValue;
                            if(matNumber.length() < 11){
                                metRequirements = false;
                            }
                            break;
                        }
                        case "date_of_birth" -> {
                            dateOfBirth = (LocalDate) cellValue;
                            
                            if(dateOfBirth.isAfter(LocalDate.now())){
                                metRequirements = false;
                            }
                            break;
                        }
                        case "gender" -> {
                            gender = (String) cellValue;
                            if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
                                metRequirements = false;
                            }
                        }
                        case "level" -> {
                            level = (String) cellValue;
                            String[] array = {"100", "200", "300", "400"};
                            if (!Arrays.asList(array).contains(level)) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "department" -> {
                            department = (String) cellValue;
                            String[] array = {"CSC", "MEE", "EEE", "CEE"};
                            if (!Arrays.asList(array).contains(department.toUpperCase())) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "email" -> {
                            email = (String) cellValue;
                            if(!currentInputMethods.checkStatus(email, "^[a-zA-Z-]+\\.[a-zA-Z-]+@pau\\.edu\\.ng$")){
                                metRequirements = false;
                            }
                            break;
                            
                        }
                    }
                    
                }
                
                if(metRequirements){
                    staffID = staffIDInput.getText();
                    rs = currentConnection.validateLecturerID(staffID, "SELECT * FROM lecturer_details WHERE staff_id = ?");
                    if(!rs.next()){
                        currentInputMethods.delayShow(staffIDWarning);
                    }else{
                        rs = currentConnection.checkIfUserExists(email, matNumber, "SELECT * FROM student_details WHERE email = ? OR mat_number = ?");
                        if(rs.next()){
                            metRequirements = false;
                        }else{
                            String dataEntryState = currentConnection.insertStudentIntoDatabase(firstName.toLowerCase(), lastName.toLowerCase(), phoneNumber, matNumber, dateOfBirth, gender, level, department, email, "INSERT INTO student_details values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            if(dataEntryState == null){
                                metRequirements = false;
                                System.out.println("An error occured somewhere");
                            }else{
                                mailSender.sendMail(firstName, email, dataEntryState);
                                rowsToRemove.add(tableView.getItems().get(row));
                            }
                        }
                    }

                }
            }
            tableView.getItems().removeAll(rowsToRemove);
            if(tableView.getItems().isEmpty()){
                successMessage.setText("Students registered successfully");
                bulkUploadTab.setVisible(true);
                successDialogBox.setVisible(true);
                tableContainer.setVisible(false);
            }
        }catch(Exception e){
            System.out.println("An error occured somewhere else " + e);
        }
    }
    
    public void initializeLecturerMap(
        ComboBox<String> departmentComboBox, 
        Map<TextField, Label> warningLecturerMap, 
        TextField lecturerStaffIDInput, 
        TextField lecturerLastNameInput, 
        TextField lecturerFirstNameInput, 
        TextField lecturerEmailInput, 
        TextField lecturerPhoneNumberInput, 
        Label firstNameWarning1, 
        Label lastNameWarning1, 
        Label phoneNumberWarning1, 
        Label emailWarning1, 
        Label staffIDWarning1,
        Map<TextField, Label> warningCourseMap,
        TextField courseName,
        Label courseNameWarning,
        TextField courseCode,
        Label courseCodeWarning,
        TextField courseStaffIDInput,
        Label staffIDCourseWarning,
        HBox setQuestionTab, 
        HBox setTestTab, 
        BorderPane setQuestionBorderPane, 
        Pane setTestPane, 
        Map<HBox, Region> questionMap,
        ComboBox<String> genderSearch,
        ComboBox<String> levelSearch,
        ComboBox<String> departmentSearch
    ){
        departmentComboBox.setItems(FXCollections.observableArrayList("CSC", "MEE", "EEE", "CEE"));
        departmentComboBox.setValue("CSC");
        warningLecturerMap.put(lecturerFirstNameInput, firstNameWarning1);
        warningLecturerMap.put(lecturerLastNameInput, lastNameWarning1);
        warningLecturerMap.put(lecturerPhoneNumberInput, phoneNumberWarning1);
        warningLecturerMap.put(lecturerEmailInput, emailWarning1);
        warningLecturerMap.put(lecturerStaffIDInput, staffIDWarning1);
        
        warningCourseMap.put(courseName, courseNameWarning);
        warningCourseMap.put(courseCode, courseCodeWarning);
        warningCourseMap.put(courseStaffIDInput, staffIDCourseWarning);
        
        questionMap.put(setQuestionTab, setQuestionBorderPane);
        questionMap.put(setTestTab, setTestPane);
        
        genderSearch.setItems(FXCollections.observableArrayList("Male", "Female"));
        levelSearch.setItems(FXCollections.observableArrayList("100", "200", "300", "400"));
        departmentSearch.setItems(FXCollections.observableArrayList("CSC", "MEE", "EEE", "CEE"));
        genderSearch.setValue("Male");
        levelSearch.setValue("100");
        departmentSearch.setValue("CSC");
    }
    
    public void changeRegistrationType(TranslateTransition translateTransition, MouseEvent event, Label lecturrRegisterButton, Label studentRegisterButton, VBox indivudualUploadTab, VBox indivudualLecturerUploadTab1, HBox bulkUploadTab){
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setCycleCount(1);
        
        if(event.getSource() == lecturrRegisterButton && !lecturrRegisterButton.getStyleClass().contains("currentlyActiveButton")){
            lecturrRegisterButton.getStyleClass().add("currentlyActiveButton");
            studentRegisterButton.getStyleClass().remove("currentlyActiveButton");
            if(!bulkUploadTab.getStyleClass().contains("selected_tab")){
                indivudualUploadTab.setVisible(false);
                indivudualLecturerUploadTab1.setVisible(true);
            }
            
            translateTransition.setToX(-150);
        }else if(event.getSource() == studentRegisterButton && !studentRegisterButton.getStyleClass().contains("currentlyActiveButton")){
            studentRegisterButton.getStyleClass().add("currentlyActiveButton");
            if(!bulkUploadTab.getStyleClass().contains("selected_tab")){
                indivudualUploadTab.setVisible(true);
                indivudualLecturerUploadTab1.setVisible(false);
            }
            
            lecturrRegisterButton.getStyleClass().remove("currentlyActiveButton");
            translateTransition.setToX(150);
        }
        
         translateTransition.playFromStart();
    }
    
    public void registerOneLecturer(            
        InputMethods currentInputMethods, 
        Map<TextField, Label> warningLecturerMap, 
        TextField lecturerEmailInput, 
        TextField lecturerPhoneNumberInput,
        Label errorMessage,
        databaseConnection currentConnection,
        TextField lecturerStaffIDInput,
        TextField lecturerFirstNameInput,
        TextField lecturerLastNameInput,
        ComboBox<String> lecturerDepartmentComboBox,
        VBox successDialogBox,
        BorderPane fromContainer,
        VBox errorDialogBox,
        Label successMessage
    ) throws SQLException, Exception{
        boolean metRequirements = currentInputMethods.checkForEmptyInputFields(warningLecturerMap);
        String email = lecturerEmailInput.getText().toLowerCase();
        email = currentInputMethods.trimEmail(email);
        email = currentInputMethods.complete(email);
        if (metRequirements){
            var phoneNumber = lecturerPhoneNumberInput.getText();
            if ((phoneNumber.startsWith("0") && phoneNumber.length() != 11) || (!phoneNumber.startsWith("0") && phoneNumber.length() != 10)){
               errorMessage.setText("Invalid phone number");
               metRequirements = false;
            }else if(!currentInputMethods.checkStatus(email, "^[a-zA-Z-]+@pau\\.edu\\.ng$")){
                metRequirements = false;
                errorMessage.setText("You can only register lecturers here");
            }else{
                ResultSet rs = currentConnection.validateLecturerID(lecturerStaffIDInput.getText(), "SELECT * FROM lecturer_details WHERE staff_id = ?");
                if(!rs.next()){
                    metRequirements = false;
                    errorMessage.setText("Invalid lecturer ID");
                }else{
                    rs = currentConnection.checkIfUserExists(email, "SELECT * FROM lecturer_details WHERE email = ?");
                    if(rs.next()){
                        metRequirements = false;
                        errorMessage.setText("Lecturer already exists");
                    }else{
                        credentials dataEntryState = currentConnection.insertLecturerIntoDatabase(lecturerFirstNameInput.getText().toLowerCase(), lecturerLastNameInput.getText().toLowerCase(), phoneNumber, lecturerDepartmentComboBox.getValue(), email, "INSERT INTO lecturer_details values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        if(dataEntryState == null){
                            metRequirements = false;
                            errorMessage.setText("An occured somewhere");
                        }else{
                            successMessage.setText("Lecturer registered successfully");
                            String staffID = dataEntryState.getStaffID();
                            String password = dataEntryState.getPassword();
                            mailSender.sendMailToLecturer(lecturerFirstNameInput.getText(), email, staffID, password);
                            successDialogBox.setVisible(true);
                            fromContainer.setDisable(true);
                            resetFields(lecturerFirstNameInput, lecturerLastNameInput, lecturerEmailInput, lecturerPhoneNumberInput, lecturerStaffIDInput, lecturerDepartmentComboBox);
                            
                        }
                    }
                }  
            } 
            if(!metRequirements){
                errorDialogBox.setVisible(true);
                fromContainer.setDisable(true); 
            }
        }
    }
    
    public void resetFields(
            TextField firstNameInput, 
            TextField lastNameInput,
            TextField emailInput, 
            TextField phoneNumberInput,
            TextField matNoInput, 
            TextField staffIDInput, 
            DatePicker birthDatePicker,
            ComboBox<String> genderComboBox,
            ComboBox<String> levelComboBox,
            ComboBox<String> departmentComboBox
            ){
        firstNameInput.setText("");
        lastNameInput.setText("");
        emailInput.setText("");
        phoneNumberInput.setText("");
        birthDatePicker.setValue(LocalDate.now());
        matNoInput.setText("");
        staffIDInput.setText("");
        genderComboBox.setValue("Male");
        levelComboBox.setValue("100");
        departmentComboBox.setValue("CSC");
    }
    
    public void resetFields(
            TextField lecturerFirstNameInput, 
            TextField lecturerLastNameInput,
            TextField lecturerEmailInput, 
            TextField lecturerPhoneNumberInput,
            TextField lecturerStaffIDInput, 
            ComboBox<String> lecturerDepartmentComboBox
            ){
        lecturerFirstNameInput.setText("");
        lecturerLastNameInput.setText("");
        lecturerEmailInput.setText("");
        lecturerPhoneNumberInput.setText("");
        lecturerStaffIDInput.setText("");
        lecturerDepartmentComboBox.setValue("CSC");
    }
    
      public void resetFields(
            TextField courseName, 
            TextField courseCode,
            ComboBox<String> departmentComboBox,
            ComboBox<String> levelComboBox,
            TextField staffID
            ){
        courseName.setText("");
        courseCode.setText("");
        staffID.setText("");
        departmentComboBox.setValue("CSC");
        levelComboBox.setValue("100");
    }
    
    
    
    public void registerManyLecturers(
            TableView adminRegisterTableLecturer,
            InputMethods currentInputMethods,
            databaseConnection currentConnection,
            Label errorMessage,
            TextField staffIDFieldLecturer,
            VBox successDialogBox,
            Label staffIDwarningLecturer,
            VBox bulkUploadTab,
            HBox lecturerTableContainer,
            Label successMessage
    ) throws SQLException{
        try{
            TableView<lecturer_model> tableView = adminRegisterTableLecturer;
            List<lecturer_model> rowsToRemove = new ArrayList<>();
            for ( int row = 0; row < tableView.getItems().size(); row++){
                boolean metRequirements = true;
                ResultSet rs;
                String firstName = "";
                String lastName = "";
                String phoneNumber = "";
                String department = "";
                String email = "";
                String staffID = "";
                for(TableColumn<lecturer_model, ?> column : tableView.getColumns()){
                    lecturer_model item = tableView.getItems().get(row);
                    Object cellValue = column.getCellObservableValue(item).getValue();
                    switch (column.getText()) {
                        
                        case "first_name" -> {
                            firstName = (String) cellValue;
                            if (firstName.isEmpty()) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "last_name" -> {
                            lastName = (String) cellValue;
                            if (lastName.isEmpty()) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "phone_number" -> {
                            phoneNumber = (String) cellValue;
                            if ((phoneNumber.startsWith("0") && phoneNumber.length() != 11) || (!phoneNumber.startsWith("0") && phoneNumber.length() != 10)) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "department" -> {
                            department = (String) cellValue;
                            String[] array = {"CSC", "MEE", "EEE", "CEE"};
                            if (!Arrays.asList(array).contains(department.toUpperCase())) {
                                metRequirements = false;
                            }
                            break;
                        }
                        case "email" -> {
                            email = (String) cellValue;
                            if(!currentInputMethods.checkStatus(email, "^[a-zA-Z-]+@pau\\.edu\\.ng$")){
                                metRequirements = false;
                            }
                            break;
                            
                        }
                    }
                    
                }
                
                if(metRequirements){
                    staffID = staffIDFieldLecturer.getText();
                    rs = currentConnection.validateLecturerID(staffID, "SELECT * FROM lecturer_details WHERE staff_id = ?");
                    if(!rs.next()){
                        currentInputMethods.delayShow(staffIDwarningLecturer);
                    }else{
                        rs = currentConnection.checkIfUserExists(email, "SELECT * FROM lecturer_details WHERE email = ?");
                        if(rs.next()){
                            metRequirements = false;
                        }else{
                            credentials dataEntryState = currentConnection.insertLecturerIntoDatabase(firstName.toLowerCase(), lastName.toLowerCase(),phoneNumber, department, email, "INSERT INTO lecturer_details values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            if(dataEntryState == null){
                                metRequirements = false;
                                System.out.println("An error occured somewhere");
                            }else{
                                String lecturerRegisteredID = dataEntryState.getStaffID();
                                String password = dataEntryState.getPassword();
                                mailSender.sendMailToLecturer(firstName, email, lecturerRegisteredID, password);
                                rowsToRemove.add(tableView.getItems().get(row));
                            }
                        }
                    }

                }
            }
            tableView.getItems().removeAll(rowsToRemove);
            if(tableView.getItems().isEmpty()){
                successMessage.setText("Lecturers registered successfully");
                bulkUploadTab.setVisible(true);
                successDialogBox.setVisible(true);
                lecturerTableContainer.setVisible(false);
            }
        }catch(Exception e){
            System.out.println("An error occured somewhere else " + e);
        }
    }
    
    public boolean createNewCourse(            
        
        Map<TextField, Label> warningCourseMap, 
        TextField  courseName,
        TextField courseCode,
        TextField courseStaffIDInput,
        
        ComboBox<String> courseDepartmentComboBox,
        ComboBox<String> courseLevelComboBox,
        
        databaseConnection currentConnection,
        InputMethods currentInputMethods, 
        Label errorMessage,
        VBox successDialogBox,
        BorderPane fromContainer,
        VBox errorDialogBox,
        Label successMessage
    ) throws SQLException{
        boolean metRequirements = currentInputMethods.checkForEmptyInputFields(warningCourseMap);
        if (metRequirements){
            ResultSet rs = currentConnection.validateLecturerID(courseStaffIDInput.getText(), "SELECT * FROM lecturer_details WHERE staff_id = ?");
            if(!rs.next()){
                    metRequirements = false;
                    errorMessage.setText("Invalid lecturer ID");
            }else{
                rs = currentConnection.checkIfCourseExists(courseCode.getText().toUpperCase(), "SELECT * FROM course_details WHERE course_id = ?");
                if(rs.next()){
                    metRequirements = false;
                    errorMessage.setText("Course already registered");
                }else{
                    int dataEntryState = currentConnection.insertCourseIntoDatabase(courseName.getText().toLowerCase(), courseCode.getText().toUpperCase(), courseDepartmentComboBox.getValue(), courseLevelComboBox.getValue(), courseStaffIDInput.getText(), "INSERT INTO course_details (course_name, course_code, level, department, staff_id) VALUES (?, ?, ?, ?, ?)");
                    if(!(dataEntryState > 0)){
                        metRequirements = false;
                        errorMessage.setText("An occured somewhere");
                    }else{
                        successMessage.setText("Course registered successfully");
                        successDialogBox.setVisible(true);
                        fromContainer.setDisable(true);
                        resetFields(courseName, courseCode, courseDepartmentComboBox, courseLevelComboBox, courseStaffIDInput);
                        return true;
                    }
                }
            }  
        } 
        if(!metRequirements){
            errorDialogBox.setVisible(true);
            fromContainer.setDisable(true); 
        }
        return false;
        
    }
    
    public void insertManyQuestions(
            TableView questionsTable,
            databaseConnection currentConnection,
            VBox bulkUploadTab,
            HBox lecturerTableContainer,
            String courseID,
            VBox successDialogBox,
            Label successMessage
    ) throws SQLException{
        try{
            TableView<question_model> tableView = questionsTable;
            List<question_model> rowsToRemove = new ArrayList<>();
            for ( int row = 0; row < tableView.getItems().size(); row++){
                question_model item = tableView.getItems().get(row);
                boolean metRequirements = true;
                
                for (question_model existingRow : rowsToRemove) {
                    if (existingRow.equals(item)) {
                        metRequirements = false;
                        break;
                    }
                }
                if (metRequirements) {
                    rowsToRemove.add(item);
                }
            }
            
            for (question_model row : rowsToRemove){
                currentConnection.insertQuestionIntoDatabase(row, courseID);  
            }
                successMessage.setText("Questions uploaded successfully");
                successDialogBox.setVisible(true);
        }catch(Exception e){
            System.out.println("An error occured somewhere else " + e);
        }
    }
    
    public void scheduleATest(            
        InputMethods currentInputMethods, 
        Map<TextField, Label> warningTestMap, 
        TextField testNameInput,
        Label errorMessage,
        DatePicker testDatePicker,
        databaseConnection currentConnection,
        ComboBox<Integer> hoursComboBox,
        ComboBox<Integer> minutesComboBox,
        ComboBox<Integer> numberOfQuestionsComboBox,
        VBox successDialogBox,
        BorderPane fromContainer,
        VBox errorDialogBox,
        String uniqueStaffID,
        String courseID,
        String courseName,
        String courseCode,
        String department,
        String level,
        ComboBox<Integer> hourTimeComboBox,
        ComboBox<Integer> minuteTimeComboBox,
        Label successMessage
    ) throws SQLException{
        boolean metRequirements = currentInputMethods.checkForEmptyInputFields(warningTestMap);
        LocalTime localTime = LocalTime.of(hourTimeComboBox.getValue(), minuteTimeComboBox.getValue());
        if (metRequirements){
            if(!testDatePicker.getValue().isAfter(LocalDate.now()) && !localTime.isAfter(LocalTime.now())){
                metRequirements = false;
                errorMessage.setText("You cannot set past or present dates");
            }else if(hoursComboBox.getValue() == 0 && minutesComboBox.getValue() == 0){
                metRequirements = false;
                errorMessage.setText("Test set time is too short");
            }else{
                int dataEntryState = currentConnection.insertTestIntoDatabase(testNameInput.getText().toLowerCase(), testDatePicker.getValue(), hoursComboBox.getValue(), minutesComboBox.getValue(), numberOfQuestionsComboBox.getValue(), uniqueStaffID, courseID, "INSERT INTO tests (test_name, test_date_time, test_duration, number_of_questions, department, level, staff_id, course_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", localTime, department, level);
                if(!(dataEntryState > 0)){
                    metRequirements = false;
                    errorMessage.setText("An occured somewhere");
                }else{
                    ResultSet rs = currentConnection.getAllStudentsForSpecificCourse(level, department);
                    while(rs.next()){
                        String email = rs.getString("email");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        
                        successMessage.setText("Test scheduled successfully");
                        successDialogBox.setVisible(true);
                        fromContainer.setDisable(true);
                        mailSender.sendMail(firstName, lastName, email, testNameInput.getText(), testDatePicker.getValue(), hoursComboBox.getValue(), minutesComboBox.getValue(), numberOfQuestionsComboBox.getValue(), courseName, courseCode, level, department, localTime);
                    }
                    testNameInput.setText("");
                }
            }
            if(!metRequirements){
                errorDialogBox.setVisible(true);
                fromContainer.setDisable(true); 
            }
        }
    }
    
    public void resetStudentFields(
            TextField firstNameInput, 
            TextField lastNameInput,
            TextField emailInput, 
            TextField phoneNumberInput,
            TextField matNoInput, 
            DatePicker birthDatePicker,
            ComboBox<String> genderComboBox,
            ComboBox<String> levelComboBox,
            ComboBox<String> departmentComboBox,
            TextField matQueryValue,
            Button resetPassword,
            Button delete,
            Button update,
            Button search
            ){
        firstNameInput.setText("");
        lastNameInput.setText("");
        emailInput.setText("");
        phoneNumberInput.setText("");
        birthDatePicker.setValue(LocalDate.now());
        matNoInput.setText("");
        genderComboBox.setValue("Male");
        levelComboBox.setValue("100");
        departmentComboBox.setValue("CSC");
        matQueryValue.setText("");
        resetPassword.setDisable(true);
        delete.setDisable(true);
        update.setDisable(true);
        search.setDisable(true);
    }
    
   
}
    
   

