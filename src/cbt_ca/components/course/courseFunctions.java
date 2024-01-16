/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.components.course;

import cbt_ca.admin_dashboard.Admin_pageController;
import cbt_ca.models.course;
import cbt_ca.reusable_functions.databaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class courseFunctions {
    databaseConnection currentConnection;

    public courseFunctions() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
    }
    public ResultSet initializeCourseList(String staffID, VBox noContentVBox, ScrollPane courseListScrollPane, VBox courseContainerVBox, Admin_pageController adminController) throws SQLException, IOException{
        ResultSet rs = currentConnection.getCourseList(staffID);
        if (rs.next()) {
            do {
                String course_id = rs.getString("course_id");
                String course_name = rs.getString("course_name");
                String course_code = rs.getString("course_code");
                String level = rs.getString("level");
                String department = rs.getString("department");

                course newCourse = new course();
                newCourse.setCourseID(course_id);
                newCourse.setCourseName(course_name);
                newCourse.setCourseCode(course_code);
                newCourse.setLevel(level);
                newCourse.setDepartment(department);
                try {
                    FXMLLoader fxmlloader = new FXMLLoader();
                    fxmlloader.setLocation(getClass().getResource("course_list.fxml"));
                    HBox courseVBox = fxmlloader.load();
                    Course_listController courseController = fxmlloader.getController();
                    courseController.setAdminPageControllerReference(adminController);

                    courseController.setData(newCourse);
                    courseVBox.setOnMouseClicked(event -> {
                        try {
                            courseController.openTestPage(newCourse);
                        } catch (SQLException ex) {
                            Logger.getLogger(courseFunctions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    courseContainerVBox.getChildren().add(courseVBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (rs.next());
        } else {
            noContentVBox.setVisible(true);
            courseListScrollPane.setVisible(false);
            System.out.println("No course yet");
        }
        return null;
    }
    
    public void refreshCourseList(String staffID, VBox noContentVBox, ScrollPane courseListScrollPane, VBox courseContainerVBox, Admin_pageController adminController) {
    try {
        courseContainerVBox.getChildren().clear();

        ResultSet rs = currentConnection.getCourseList(staffID);
        initializeCourseList(rs, noContentVBox, courseListScrollPane, courseContainerVBox, adminController);

    } catch (SQLException | IOException e) {
        e.printStackTrace(); 
    }
}

public void initializeCourseList(ResultSet rs, VBox noContentVBox, ScrollPane courseListScrollPane, VBox courseContainerVBox, Admin_pageController adminController) throws SQLException, IOException {
    if (rs.next()) {
        do {
            String course_id = rs.getString("course_id");
            String course_name = rs.getString("course_name");
            String course_code = rs.getString("course_code");
            String level = rs.getString("level");
            String department = rs.getString("department");

            course newCourse = new course();
            newCourse.setCourseID(course_id);
            newCourse.setCourseName(course_name);
            newCourse.setCourseCode(course_code);
            newCourse.setLevel(level);
            newCourse.setDepartment(department);

            try {
                    FXMLLoader fxmlloader = new FXMLLoader();
                    fxmlloader.setLocation(getClass().getResource("course_list.fxml"));
                    HBox courseVBox = fxmlloader.load();
                    Course_listController courseController = fxmlloader.getController();
                    courseController.setAdminPageControllerReference(adminController);
                    courseController.setData(newCourse);
                    courseVBox.setOnMouseClicked(event -> {
                        try {
                            courseController.openTestPage(newCourse);
                        } catch (SQLException ex) {
                            Logger.getLogger(courseFunctions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    courseContainerVBox.getChildren().add(courseVBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } while (rs.next());
    } else {
        noContentVBox.setVisible(true);
        courseListScrollPane.setVisible(false);
        System.out.println("No course yet");
    }
}



}
