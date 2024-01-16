/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import cbt_ca.models.lecturer_model;
import cbt_ca.models.question_model;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cbt_ca.models.student_model;
import cbt_ca.models.student_score_model;
import com.mysql.cj.jdbc.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class CSVAccessMethods {
    public void invertCSVToTable(HBox button, TableView table, VBox uploadButon, HBox tableContainer) throws IOException { 
        table.setEditable(true);
        Stage currentStage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload a new CSV file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        
        if (selectedFile != null) {
            String filename = selectedFile.getAbsolutePath();
            String file = selectedFile.getName();
            if(!filename.contains(".csv")){
                filename = filename + ".csv";
            }
            uploadButon.setVisible(false);
            tableContainer.setVisible(true);
            Stream<String> lines = Files.lines(Paths.get(filename));

            // Parse the CSV file into a list of Student objects
            ObservableList<student_model> students = FXCollections.observableArrayList();
            lines.forEach(line -> {
             String[] fields = line.split(",");
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
             LocalDate birthDate = LocalDate.parse(fields[4], formatter); // Assuming birthDate is the 5th column
             student_model student = new student_model(fields[0], fields[1], fields[2], fields[3], birthDate, fields[5], fields[6], fields[7], fields[8]);
             students.add(student);
            });

            // Bind the TableView to the list of Student objects
            table.setItems(students);

        } else {
            System.out.println("No selected file");
        }
        
    } 
    
    public void invertLecturerCSVToTable(HBox button, TableView table, VBox uploadButon, HBox tableContainer) throws IOException { 
        table.setEditable(true);
        Stage currentStage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload a new CSV file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        
        if (selectedFile != null) {
            String filename = selectedFile.getAbsolutePath();
            String file = selectedFile.getName();
            if(!filename.contains(".csv")){
                filename = filename + ".csv";
            }
            uploadButon.setVisible(false);
            tableContainer.setVisible(true);
            Stream<String> lines = Files.lines(Paths.get(filename));

            // Parse the CSV file into a list of Student objects
            ObservableList<lecturer_model> lecturers = FXCollections.observableArrayList();
            lines.forEach(line -> {
             String[] fields = line.split(",");
             lecturer_model lecturer = new lecturer_model(fields[0], fields[1], fields[2], fields[3], fields[4]);
             lecturers.add(lecturer);
            });

            // Bind the TableView to the list of Student objects
            table.setItems(lecturers);

        } else {
            System.out.println("No selected file");
        }
        
    } 
    
    
    public void initialTable(
            TableColumn<student_model, String> firstNameColumn, 
            TableColumn<student_model, String> lastNameColumn,
            TableColumn<student_model, String> phoneNumberColumn,
            TableColumn<student_model, String> matNumberColumn,
            TableColumn<student_model, LocalDate> birthDateColumn,
            TableColumn<student_model, String> genderColumn,
            TableColumn<student_model, String> levelColumn,
            TableColumn<student_model, String> departmentColumn,
            TableColumn<student_model, String> emailColumn
            ){
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setEditable(true);
        
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setEditable(true);
        
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setEditable(true);
        
        matNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        matNumberColumn.setCellValueFactory(new PropertyValueFactory<>("matNumber"));
        matNumberColumn.setEditable(true);
        
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        birthDateColumn.setEditable(true);
        
        genderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setEditable(true);
        
        levelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        levelColumn.setEditable(true);
        
        departmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentColumn.setEditable(true);
        
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setEditable(true);
    }
    
    public void initialTable(
            TableColumn<lecturer_model, String> firstNameColumn, 
            TableColumn<lecturer_model, String> lastNameColumn,
            TableColumn<lecturer_model, String> phoneNumberColumn,
            TableColumn<lecturer_model, String> departmentColumn,
            TableColumn<lecturer_model, String> emailColumn
            ){
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setEditable(true);
        
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setEditable(true);
        
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setEditable(true);
        
        departmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentColumn.setEditable(true);
        
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setEditable(true);
    }
    
    public void initialQuestionTable(
            TableColumn<question_model, String> question_column, 
            TableColumn<question_model, String> option_a_column,
            TableColumn<question_model, String> option_b_column,
            TableColumn<question_model, String> option_c_column,
            TableColumn<question_model, String> option_d_column,
            TableColumn<question_model, String> correct_option_column
            ){
        question_column.setCellFactory(TextFieldTableCell.forTableColumn());
        question_column.setCellValueFactory(new PropertyValueFactory<>("question"));
        question_column.setEditable(true);
        
        option_a_column.setCellFactory(TextFieldTableCell.forTableColumn());
        option_a_column.setCellValueFactory(new PropertyValueFactory<>("optionA"));
        option_a_column.setEditable(true);
        
        option_b_column.setCellFactory(TextFieldTableCell.forTableColumn());
        option_b_column.setCellValueFactory(new PropertyValueFactory<>("optionB"));
        option_b_column.setEditable(true);
        
        option_c_column.setCellFactory(TextFieldTableCell.forTableColumn());
        option_c_column.setCellValueFactory(new PropertyValueFactory<>("optionC"));
        option_c_column.setEditable(true);
        
        option_d_column.setCellFactory(TextFieldTableCell.forTableColumn());
        option_d_column.setCellValueFactory(new PropertyValueFactory<>("optionD"));
        option_d_column.setEditable(true);
        
        correct_option_column.setCellFactory(TextFieldTableCell.forTableColumn());
        correct_option_column.setCellValueFactory(new PropertyValueFactory<>("correctOption"));
        correct_option_column.setEditable(true);
    }
    
    public void convertCSVToQuestionTable(Button button, TableView table) throws IOException { 
        table.setEditable(true);
        Stage currentStage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload a new question set");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        
        if (selectedFile != null) {
            String filename = selectedFile.getAbsolutePath();
            String file = selectedFile.getName();
            if(!filename.contains(".csv")){
                filename = filename + ".csv";
            }
            Stream<String> lines = Files.lines(Paths.get(filename));

            // Parse the CSV file into a list of Questions objects
            ObservableList<question_model> questionsFromFile = FXCollections.observableArrayList();
            lines.forEach(line -> {
             String[] fields = line.split(",");
             question_model question = new question_model(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
             questionsFromFile.add(question);
            });
            ObservableList<question_model> existingData = table.getItems();
            existingData.addAll(questionsFromFile);
            table.setItems(existingData);

        } else {
            System.out.println("No selected file");
        }
    }
    
    public void addNewRow(TableView table){
        ObservableList<question_model> questions = table.getItems();
        questions.add(new question_model("Question", "Option A", "Option B", "Option C", "Option D", "A"));

    }
    
    public void getDataFromDatabase(String courseID, TableView questionsTable, databaseConnection currentConnection) throws SQLException{
        List<question_model> setOfQuestions = currentConnection.getAllQuestions(courseID);
        ObservableList<question_model> observableQuestions = FXCollections.observableList(setOfQuestions);
        questionsTable.setItems(observableQuestions);   
    }
    
    
    public void initilizeStudentScoresTable(
            TableColumn<student_score_model, String> test_name_column, 
            TableColumn<student_score_model, String> number_of_questions_column,
            TableColumn<student_score_model, String> score_column,
            TableColumn<student_score_model, String> mat_number_score_column,
            TableColumn<student_score_model, String> selected_optons_column
            ){
        test_name_column.setCellFactory(TextFieldTableCell.forTableColumn());
        test_name_column.setCellValueFactory(new PropertyValueFactory<>("testName"));
        
        number_of_questions_column.setCellFactory(TextFieldTableCell.forTableColumn());
        number_of_questions_column.setCellValueFactory(new PropertyValueFactory<>("numberOfQuestions"));
        
        score_column.setCellFactory(TextFieldTableCell.forTableColumn());
        score_column.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        mat_number_score_column.setCellFactory(TextFieldTableCell.forTableColumn());
        mat_number_score_column.setCellValueFactory(new PropertyValueFactory<>("matNumber"));
        
        selected_optons_column.setCellFactory(TextFieldTableCell.forTableColumn());
        selected_optons_column.setCellValueFactory(new PropertyValueFactory<>("selectedOptions"));
    }
}
