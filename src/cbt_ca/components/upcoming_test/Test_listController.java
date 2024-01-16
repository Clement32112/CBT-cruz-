/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.components.upcoming_test;

import cbt_ca.models.test_node;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.student_dashboard.Student_pageController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author HP PROBOOK 430 G3
 */
public class Test_listController{

    @FXML
    private HBox card;
    @FXML
    private Label course_name;
    @FXML
    private Label dept_and_level;
    
    private Student_pageController student_pageController;
    private databaseConnection currentConnection;
    
    
    public Test_listController() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
    }
    
    
    public void setStudentPageControllerReference(Student_pageController student_pageController) {
        this.student_pageController = student_pageController;
    }
    
    public void setData(test_node testNode){
            course_name.setText(testNode.getTestName() + "  " + testNode.getNumberOfQuestions() + " questions");
            dept_and_level.setText(testNode.getTestDate() + "  " + testNode.getDepartment() + " - " + testNode.getLevel());
    }
    
    public void openTestPage(test_node newTestNode, Integer state) throws SQLException {
        ResultSet rs = currentConnection.pickACourse(newTestNode.getCourseID());
        if(rs.next()){
            student_pageController.openSetTestPage(newTestNode.getTestID(), newTestNode.getTestName(), newTestNode.getTestDate(), newTestNode.getDuration(), newTestNode.getNumberOfQuestions(),newTestNode.getDepartment(), newTestNode.getLevel(), rs.getString(2), rs.getString(3), state, newTestNode.getCourseID());
        
        }
        
        
    }
    
    
}
