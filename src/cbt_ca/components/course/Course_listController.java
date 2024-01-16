/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.components.course;

import cbt_ca.admin_dashboard.Admin_pageController;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.models.course;
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
public class Course_listController{

    @FXML
    private HBox card;
    @FXML
    private Label course_name;
    @FXML
    private Label dept_and_level;
    
    private Admin_pageController adminPageController;
    
    

    public void setAdminPageControllerReference(Admin_pageController adminPageController) {
        this.adminPageController = adminPageController;
    }

    public void openTestPage(course newCourse) throws SQLException {
            adminPageController.openSetTestPage(newCourse.getCourseID(), newCourse.getCourseName(), newCourse.getCourseCode(), newCourse.getDepartment(), newCourse.getLevel());
    }
    
    public void setData(course newCourse){
            course_name.setText(newCourse.getCourseCode() + " - " + newCourse.getCourseName());
            dept_and_level.setText(newCourse.getDepartment() + " - " + newCourse.getLevel());
    }   
    
}
