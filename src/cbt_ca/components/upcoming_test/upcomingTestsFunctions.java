/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.components.upcoming_test;

import cbt_ca.models.test_node;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.student_dashboard.Student_pageController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class upcomingTestsFunctions {
    
    databaseConnection currentConnection;
    
    public upcomingTestsFunctions() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
    }
    
    
    public ResultSet initializeUpcomingTests(String department, String level, Label noTestNotification, ScrollPane upcomingTestsScrollPane, VBox upcomingTestsContainer, Student_pageController studentController) throws SQLException, IOException{
        ResultSet rs = currentConnection.getCourseList(department, level);
        if (rs.next()) {
            do {
                String test_id = rs.getString("test_id");
                String test_name = rs.getString("test_name");
                Timestamp test_date = rs.getTimestamp("test_date_time");
                Integer test_duration = rs.getInt("test_duration");
                Integer number_of_questions = rs.getInt("number_of_questions");
                String testDepartment = rs.getString("department");
                String testLevel = rs.getString("level");
                String courseID = rs.getString("course_id");
                test_node newTestNode = new test_node();
                newTestNode.setTestID(test_id);
                newTestNode.setTestName(test_name);
                newTestNode.setTestDate(test_date);
                newTestNode.setDuration(test_duration);
                newTestNode.setNumberOfQuestions(number_of_questions);
                newTestNode.setDepartment(testDepartment);
                newTestNode.setLevel(testLevel);
                newTestNode.setCourseID(courseID);
                try {
                    FXMLLoader fxmlloader = new FXMLLoader();
                    fxmlloader.setLocation(getClass().getResource("test_list.fxml"));
                    HBox TestHBox = fxmlloader.load();
                    Test_listController testController = fxmlloader.getController();
                    testController.setStudentPageControllerReference(studentController);

                    testController.setData(newTestNode);
                    TestHBox.setOnMouseClicked(event -> {
                        
                        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                        
                        long testTimestampMillis = test_date.getTime();
                        long currentTimestampMillis = currentTimestamp.getTime();
                        try {
                            if((testTimestampMillis + (test_duration * 60 * 1000)) < currentTimestampMillis){
                                testController.openTestPage(newTestNode, 1); // late
                            }else if((currentTimestampMillis + (10 * 60 * 1000)) < testTimestampMillis){
                                testController.openTestPage(newTestNode, -1); // early
                            }else{
                                testController.openTestPage(newTestNode, 0); // during
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(upcomingTestsFunctions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    });
                    upcomingTestsContainer.getChildren().add(TestHBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (rs.next());
        } else {
            noTestNotification.setVisible(true);
            upcomingTestsContainer.setVisible(false);
            System.out.println("No course yet");
        }
        return null;
    }
}
