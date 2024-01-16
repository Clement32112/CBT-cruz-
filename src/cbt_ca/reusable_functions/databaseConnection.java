/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import cbt_ca.models.credentials;
import cbt_ca.models.question_model;
import cbt_ca.models.student_score_model;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import cbt_ca.reusable_functions.PasswordGenerator;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;



/**
 *
 * @author HP PROBOOK 430 G3
 */
public class databaseConnection {
    private final Connection con;
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();
    
    public databaseConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt_ca?user=root&password=serialKiller");
    };
    
    public ResultSet loginUser(String email, String password, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public ResultSet checkIfUserExists(String email, String matNumber, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, matNumber);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public ResultSet checkIfCourseExists(String course_code, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, course_code);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public ResultSet checkIfUserExists(String email, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public ResultSet validateLecturerID(String ID, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public ResultSet getCourseList(String staffID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM course_details WHERE staff_id = ?");
        ps.setString(1, staffID);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public ResultSet getCourseList(String department, String level) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM tests WHERE department = ? AND level = ?");
        ps.setString(1, department);
        ps.setString(2, level);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public void uploadImage(Image image, String tableType, String emailAddress) throws FileNotFoundException, IOException, SQLException{
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", s);
        byte[] imageBytes = s.toByteArray();
        SerialBlob imageBlob = new SerialBlob(imageBytes);

        // Insert or update the database
        String sql = "UPDATE " + tableType + " SET profile_photo = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setBlob(1, imageBlob);
            preparedStatement.setString(2, emailAddress);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Photo column successfully updated.");
            } else {
                System.out.println("No rows were updated.");
            }
        }
    }
    
    
    public Image imageDownload(String tableType, String emailAddress) throws SQLException{
        String selectSql = "SELECT profile_photo FROM " + tableType + " WHERE email = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(selectSql)) {
            preparedStatement.setString(1, emailAddress);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    java.sql.Blob columnValue = resultSet.getBlob("profile_photo");

                    if (columnValue != null) {
                        byte[] imageData = columnValue.getBytes(1, (int) columnValue.length());
                        Image image = new Image(new ByteArrayInputStream(imageData));
                        return image;
                    } else {
                        System.out.println("Profile photo is null.");
                    }
                } else {
                    System.out.println("No matching record found.");
                }
            }
        } catch (SQLException e) {
            // Handle SQLException
        e.printStackTrace();
    }

    return null;
    }
    
    
    
    public String insertStudentIntoDatabase(String firstName, String lastName, String phoneNumber, String matNumber, LocalDate dateOfBirth, String gender, String level, String department, String email, String query) throws SQLException, Exception {
        
        String password = passwordGenerator.generatePassword();
        String salt = PasswordHashing.getSalt();
        String hashedPassword = PasswordHashing.hashPassword(password, salt);
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, matNumber);
            preparedStatement.setDate(5, Date.valueOf(dateOfBirth));
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, level);
            preparedStatement.setString(8, department);
            preparedStatement.setString(9, hashedPassword);
            preparedStatement.setString(10, email);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, salt);
            int rs = preparedStatement.executeUpdate();
            if(rs > 0){
                return password;
            }
            return null;
        }
    }
    
    public credentials insertLecturerIntoDatabase(String firstName, String lastName, String phoneNumber, String department, String email, String query) throws SQLException, Exception {
        
        String password = passwordGenerator.generatePassword();
        boolean bool = false;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM lecturer_details WHERE staff_id = ?");
        while(!bool){
            String staffID = passwordGenerator.generateIDNumber();
            ps.setString(1, staffID);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                bool = true;
                String salt = PasswordHashing.getSalt();
                String hashedPassword = PasswordHashing.hashPassword(password, salt);
                System.out.println(password);
                try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    preparedStatement.setString(1, staffID);
                    preparedStatement.setString(2, firstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, hashedPassword);
                    preparedStatement.setString(6, department);
                    preparedStatement.setString(7, phoneNumber);
                    preparedStatement.setString(8, null);
                    preparedStatement.setString(9, salt);
                    int result = preparedStatement.executeUpdate();
                    if(result > 0){
                        return new credentials(staffID, password);
                    }
                }
            }
        }  
        return null;
    }
    
    public int insertCourseIntoDatabase(
            String courseName, String courseCode, String department, String level, String staffID, String query) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseCode);
            preparedStatement.setString(3, level);
            preparedStatement.setString(4, department);
            preparedStatement.setString(5, staffID);
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
    }
    
    public int insertQuestionIntoDatabase(question_model row, String courseID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO test_questions (question, option_a, option_b,  option_c, option_d,  correct_option, course_id) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE " +
                         "question = VALUES(question), option_a = VALUES(option_a), option_b = VALUES(option_b), option_c = VALUES(option_c), option_d = VALUES(option_d), correct_option = VALUES(correct_option), course_id = VALUES(course_id)");
        
        ps.setString(1, row.getQuestion());
        ps.setString(2, row.getOptionA());
        ps.setString(3, row.getOptionB());
        ps.setString(4, row.getOptionC());
        ps.setString(5, row.getOptionD());
        ps.setString(6, row.getCorrectOption());
        ps.setString(7, courseID);
        
        int rs = ps.executeUpdate();
        return rs;
    }
    
    public List<question_model> getAllQuestions(String courseID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM test_questions WHERE course_id = ?");
        
        ps.setString(1, courseID);
        
        ResultSet rs = ps.executeQuery();
        List<question_model> questions = new ArrayList<>();
        while (rs.next()) {
                // Extract data from the result set
                String questionText = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");

                question_model question = new question_model(questionText, optionA, optionB, optionC, optionD, correctOption);

                questions.add(question);
        }

        return questions;
    }
    
    public int insertTestIntoDatabase(String testName, LocalDate testDatePicker, Integer hours, Integer minutes, Integer numberOfQuestions, String staffID, String courseID, String query, LocalTime time, String department, String level) throws SQLException {
        int totalMinutes = (hours * 60) + minutes;
        LocalDateTime testDateTime = LocalDateTime.of(testDatePicker, time);
        Timestamp testTimestamp = Timestamp.valueOf(testDateTime);
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, testName);
            preparedStatement.setTimestamp(2, testTimestamp);
            preparedStatement.setInt(3, totalMinutes);
            preparedStatement.setInt(4, numberOfQuestions);
            preparedStatement.setString(5, department);
            preparedStatement.setString(6, level);
            preparedStatement.setString(7, staffID);
            preparedStatement.setString(8, courseID);
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
    }
    
    public int insertTestScriptIntoDatabase(String[] yourAnswers, Integer numberOfQuestions, Integer score, Integer testID, String matNumber ) throws SQLException {
        String joinedAnswers = String.join(" ", yourAnswers);
        try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO test_scores (selected_options, number_of_questions, score, test_id, mat_number) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, joinedAnswers);
            preparedStatement.setInt(2, numberOfQuestions);
            preparedStatement.setInt(3, score);
            preparedStatement.setInt(4, testID);
            preparedStatement.setString(5, matNumber);
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
    }
    
    public ResultSet getAllStudentsForSpecificCourse(String level, String department) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * FROM student_details WHERE level = ? AND department = ?");
        ps.setString(1, level);
        ps.setString(2, department);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet pickACourse(String course_id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM course_details where course_id = ?");
        ps.setString(1, course_id);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    
    public void getTestQuestionsFromDataBase(
            String query, 
            String course_id, 
            Integer numberOfQuestions,
            ArrayList<String> questionsList, 
            ArrayList<String> correctOptionsList,
            ArrayList<String> optionAList,
            ArrayList<String> optionBList,
            ArrayList<String> optionCList,
            ArrayList<String> optionDList
    ) throws SQLException{
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, course_id);
        ResultSet rs = ps.executeQuery();
        int index = 0;
        while (rs.next() && index < numberOfQuestions) {
            // Extract data from the result set
            questionsList.add(rs.getString("question"));
            correctOptionsList.add(rs.getString("correct_option"));
            optionAList.add(rs.getString("option_a"));
            optionBList.add(rs.getString("option_b"));
            optionCList.add(rs.getString("option_c"));
            optionDList.add(rs.getString("option_d"));

        }
    };
    
    public void updatePasswprd(String password, String matNumber) throws SQLException, Exception{
        String salt = PasswordHashing.getSalt();
        String hashedPassword = PasswordHashing.hashPassword(password, salt);
        String sql = "UPDATE student_details SET password = ?, salt = ? WHERE mat_number = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, salt);
            preparedStatement.setString(3, matNumber);
            int rs = preparedStatement.executeUpdate();
        }
    }
    
    public int deleteStudent(String matNumber) throws SQLException {
        String sql = "DELETE FROM student_details WHERE mat_number = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, matNumber);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected;
        }
    }
    public int updateStudentInDatabase(String firstName, String lastName, String phoneNumber, String matNumber, LocalDate dateOfBirth, String gender, String level, String department, String email) throws SQLException {
        
        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE student_details SET first_name = ?, last_name = ?, phone_number = ?, date_of_birth = ?, gender = ?, level = ?, department = ?, email = ? WHERE mat_number = ?")) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setDate(4, Date.valueOf(dateOfBirth));
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, level);
            preparedStatement.setString(7, department);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, matNumber);
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
    }
    
    public void getAllScoresFromDatabase(String staffID, TableView table, ComboBox<String> filterMode, Button downloadTableButton) throws SQLException {
   try (PreparedStatement preparedStatement = con.prepareStatement("SELECT test_scores.*, tests.test_name FROM test_scores JOIN tests ON test_scores.test_id = tests.test_id WHERE tests.staff_id = ?")) {
       preparedStatement.setString(1, staffID);
       ResultSet rs = preparedStatement.executeQuery();
       ObservableList<student_score_model> testScores = FXCollections.observableArrayList();
       ObservableList <String> filters = FXCollections.observableArrayList();
       while (rs.next()) {
           String testName = rs.getString("test_name");
           String matNumber = rs.getString("mat_number");
           String numberOfQuestions = rs.getString("number_of_questions");
           String score = rs.getString("score");
           String selectedOptions = rs.getString("selected_options");

           filters.add(testName);

           // Create a TestScore object and add it to the observable list
           testScores.add(new student_score_model(testName, matNumber, numberOfQuestions, score, selectedOptions));
       }

       if (!testScores.isEmpty()) {
           filters.add(0, "All tests");
           downloadTableButton.setDisable(false);

           // Create a FilteredList from the testScores list
           FilteredList<student_score_model> filteredData = new FilteredList<>(testScores, p -> true);
           // Set the items of the TableView to the FilteredList
           SortedList<student_score_model> sortedData = new SortedList<>(filteredData);
           sortedData.comparatorProperty().bind(table.comparatorProperty());
           table.setItems(sortedData);

           // Set the items of the ComboBox to the filters list
           filterMode.setItems(filters);
           filterMode.setValue("All tests");

           // Add a ChangeListener to the ComboBox
           filterMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if ("All tests".equals(newValue)) {
                    filteredData.setPredicate(p -> true);
                } else {
                    filteredData.setPredicate(p -> p.getTestName().equals(newValue));
                }
            });
       } 
   }
}
    
     public ResultSet checkIfTestHasBeenTakenExists(String testID, String matNumber) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM test_scores WHERE test_id = ? AND mat_number = ?");
        ps.setString(1, testID);
        ps.setString(2, matNumber);
        ResultSet rs = ps.executeQuery();
        return rs;
    };

    
}


